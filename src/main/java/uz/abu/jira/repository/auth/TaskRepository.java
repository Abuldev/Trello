package uz.muhammad.jira.repository.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.TaskCriteria;
import uz.muhammad.jira.domains.auth.Column;
import uz.muhammad.jira.domains.auth.Task;
import uz.muhammad.jira.repository.GenericCRUDRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  14:50 (Friday)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskRepository implements GenericCRUDRepository<Task, TaskCriteria, Long> {

    private static TaskRepository instance;

    private static final List<Task> tasks = load();


//    public static Gson gson = ApplicationContextHolder.getBean(Gson.class);

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<Task> load() {
        // TODO: 6/15/2022 load data from file here

        try{

            FileReader reader = new FileReader("src/main/resources/tasks.json");
            Type type = new TypeToken<List<Task>>(){}.getType();
            List<Task> taskList = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, type);
            if (taskList==null){
                taskList = new ArrayList<>();
            }
            return taskList;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void saveToJson() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/tasks.json");
            fileWriter.write(gson.toJson(tasks));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void create(Task entity) {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(LocalDateTime.now().toString());
        tasks.add(entity);
        saveToJson();
    }

    @Override
    public void update(Task entity) {
        tasks.add(entity);
        saveToJson();
    }

    @Override
    public boolean deleteByID(Long id) {
        for (Task task : tasks) {
            if(task.getId().equals(id)){
                tasks.remove(task);
                saveToJson();
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Task>> findAll(TaskCriteria criteria) {
        return Optional.of(tasks);
    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    public Optional<Task> findByUsername(String name) {
        return tasks.stream()
                .filter(task -> task.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}