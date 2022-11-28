package uz.muhammad.jira.repository.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.OrgCriteria;
import uz.muhammad.jira.domains.auth.Organization;
import uz.muhammad.jira.repository.GenericCRUDRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 16/06/22  14:55 (Thursday)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrgRepository implements GenericCRUDRepository<Organization, OrgCriteria, Long> {

    private static OrgRepository instance;

    private static List<Organization> organizations = load();

//    private static Gson gson = ApplicationContextHolder.getBean(Gson.class);
public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static List<Organization> load() {
        // TODO: 6/15/2022 load data from file here

        Type type = new TypeToken<List<Organization>>() {
        }.getType();

        try(FileReader fileReader = new FileReader("src/main/resources/orgs.json");) {
           List<Organization> orgs = new GsonBuilder().setPrettyPrinting().create().fromJson(fileReader,type);
           if (orgs==null){
               orgs = new ArrayList<>();
           }
            return orgs;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void saveToJson() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/orgs.json");
            fileWriter.write(gson.toJson(organizations));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public void create(Organization entity) {
        organizations.add(entity);
        saveToJson();
    }

    @Override
    public void update(Organization entity) {
        organizations.set(organizations.indexOf(entity), entity);
        saveToJson();
    }

    @Override
    public boolean deleteByID(Long id) {
        for (int i = 0; i < organizations.size(); i++) {
            if (organizations.get(i).getId().equals(id)){
                organizations.get(i).setDeleted(true);
                return true;
            }
        }
        saveToJson();
        return false;
    }

    @Override
    public Optional<Organization> findById(Long id) {
        return organizations.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Organization>> findAll(OrgCriteria criteria) {
        return Optional.of(organizations);
    }

//    public Optional<List<Organization>> findAllById(Long id) {
//        return Optional.of(organizations.stream().filter(org -> org.getOwnerId().equals(id)).toList());
//    }

    public static OrgRepository getInstance() {
        if (instance == null) {
            instance = new OrgRepository();
        }
        return instance;
    }

    public Optional<Organization> findByUsername(String name) {
        return organizations.stream()
                .filter(organization -> organization.getName().equalsIgnoreCase(name))
                .findFirst();
    }


}
