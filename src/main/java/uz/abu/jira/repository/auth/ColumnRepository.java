package uz.muhammad.jira.repository.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.ColumnCriteria;
import uz.muhammad.jira.domains.auth.Column;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.mappers.ColumnMapper;
import uz.muhammad.jira.repository.GenericCRUDRepository;
import uz.muhammad.jira.services.auth.ColumnService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColumnRepository implements GenericCRUDRepository<Column, ColumnCriteria, Long> {

    private static ColumnRepository instance;
    private static final List<Column> columns = load();

    public static List<Column> load() {
        return new ArrayList<>();
    }


    @Override
    public void create(Column entity) {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(LocalDateTime.now().toString());
        columns.add(entity);
    }

    @Override
    public void update(Column entity) {

    }

    @Override
    public boolean deleteByID(Long aLong) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getId().equals(aLong)){
                columns.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Column> findById(Long id) {
        return columns.stream()
                .filter(column -> column.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Column>> findAll(ColumnCriteria criteria) {
        return Optional.of(columns);
    }

    public static ColumnRepository getInstance() {
        if (instance == null) {
            instance = new ColumnRepository();
        }
        return instance;
    }

    public Optional<Column> findByUsername(String name) {
        return columns.stream()
                .filter(column -> column.getName().equals(name))
                .findFirst();
    }
}

