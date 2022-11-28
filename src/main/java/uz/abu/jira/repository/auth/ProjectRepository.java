package uz.muhammad.jira.repository.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.ProjectCriteria;
import uz.muhammad.jira.domains.auth.Project;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.repository.GenericCRUDRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  14:50 (Friday)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectRepository implements GenericCRUDRepository<Project, ProjectCriteria, Long> {

    private static ProjectRepository instance;
//    public static Gson gson = ApplicationContextHolder.getBean(Gson.class);
public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final List<Project> projects = load();


    public static List<Project> load() {
        // TODO: 6/15/2022 load data from file here

        try{

            FileReader reader = new FileReader("src/main/resources/projects.json");
            Type type = new TypeToken<List<Project>>(){}.getType();
            List<Project> projectList = gson.fromJson(reader, type);
            if (projectList==null){
                projectList = new ArrayList<>();
            }
            return projectList;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void saveToJson() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/projects.json");
            fileWriter.write(gson.toJson(projects));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void create(Project entity) {
        projects.add(entity);
        saveToJson();
    }

    @Override
    public void update(Project entity) {
        projects.set(projects.indexOf(entity), entity);
        saveToJson();
    }

    @Override
    public boolean deleteByID(Long id) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getId().equals(id)){
                projects.get(i).setDeleted(true);
                saveToJson();
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projects.stream()
                .filter(project -> project.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Project>> findAll(ProjectCriteria criteria) {
        return Optional.of(projects);
    }

    public static ProjectRepository getInstance() {
        if (instance == null) {
            instance = new ProjectRepository();
        }
        return instance;
    }

    public Optional<Project> findByUsername(String name) {
        return projects.stream()
                .filter(project -> project.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}