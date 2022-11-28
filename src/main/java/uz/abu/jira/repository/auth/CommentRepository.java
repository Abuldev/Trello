package uz.muhammad.jira.repository.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.CommentCriteria;
import uz.muhammad.jira.domains.auth.Column;
import uz.muhammad.jira.domains.auth.Comment;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentRepository implements GenericCRUDRepository<Comment, CommentCriteria, Long> {

    private static CommentRepository instance;
    private static final List<Comment> comments = load();


    //    public static Gson gson = ApplicationContextHolder.getBean(Gson.class);
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<Comment> load() {
        // TODO: 6/15/2022 load data from file here

        try {

            FileReader reader = new FileReader("src/main/resources/comments.json");
            Type type = new TypeToken<List<Comment>>() {
            }.getType();
            List<Comment> commnetList = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, type);
            if (commnetList == null) {
                commnetList = new ArrayList<>();
            }
            return commnetList;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveToJson() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/comments.json");
            fileWriter.write(gson.toJson(comments));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void create(Comment entity) {
        entity.setId(System.currentTimeMillis());
        entity.setCreatedAt(LocalDateTime.now().toString());
        comments.add(entity);
        saveToJson();
    }

    @Override
    public void update(Comment entity) {
        saveToJson();
    }

    @Override
    public boolean deleteByID(Long aLong) {
        saveToJson();
        return false;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Comment>> findAll(CommentCriteria criteria) {
//        return Optional.of(comments);
        return null;
    }

    public static CommentRepository getInstance() {
        if (instance == null) {
            instance = new CommentRepository();
        }
        return instance;
    }
}
