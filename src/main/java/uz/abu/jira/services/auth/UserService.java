package uz.muhammad.jira.services.auth;

import com.google.gson.reflect.TypeToken;
import lombok.NonNull;
import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.OrgCriteria;
import uz.muhammad.jira.criteria.UserCriteria;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.mappers.UserMapper;
import uz.muhammad.jira.repository.AbstractRepository;
import uz.muhammad.jira.repository.auth.UserRepository;
import uz.muhammad.jira.services.GenericCRUDService;
import uz.muhammad.jira.vo.auth.orgVO.OrgVO;
import uz.muhammad.jira.vo.auth.userVO.UserCreateVO;
import uz.muhammad.jira.vo.auth.userVO.UserUpdateVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ErrorVO;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 */


public class UserService extends AbstractRepository<UserRepository, UserMapper> implements
        GenericCRUDService<UserVO, UserCreateVO, UserUpdateVO, UserCriteria, Long> {

    private static UserService instance;

    private UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ResponseEntity<Data<Long>> create(@NonNull UserCreateVO dto) {
        Optional<User> userOptional = repository.findByUsername(dto.getUserName());
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(new Data<>(ErrorVO
                    .builder()
                    .friendlyMessage("User Name '%s' already taken".formatted(dto.getUserName()))
                    .status(400)
                    .build()));
        }
        UserVO userVO = new UserVO();
        userVO.setId(System.currentTimeMillis());
        userVO.setUserName(dto.getUserName());
        userVO.setPassword(dto.getPassword());
        userVO.setEmail(dto.getEmail());

        repository.create(mapper.getUser(userVO));

        return new ResponseEntity<>(new Data<>(userVO.getId()));
    }

    @Override
    public ResponseEntity<Data<String>> delete(@NonNull Long id) {
        Optional<User> userOptional = repository.findById(id);

        ResponseEntity<Data<String>> response;

        if (userOptional.isEmpty()){
            return new ResponseEntity<>(new Data<>(new ErrorVO("ID not found", "ID not found", 500)));
        }

        UserVO userVO = mapper.getUserVO(userOptional.get());
        repository.deleteByID(mapper.getUser(userVO).getId());
        Data<String> data = new Data<>("User deleted");
         response = new ResponseEntity<>(data);
        return response;
    }

    @Override
    public ResponseEntity<Data<String>> update(@NonNull UserUpdateVO dto) {

        Optional<User> userOptional = repository.findById(dto.getId());

        if (userOptional.isEmpty()){
            return new ResponseEntity<>(new Data<>(new ErrorVO("ID not found", "ID not found", 500)));
        }

        UserVO userVO = mapper.getUserVO(userOptional.get());
        userVO.setUserName(dto.getUserName());
        userVO.setPassword(dto.getPassword());
        userVO.setUpdatedAt(LocalDateTime.now().toString());
        repository.update(mapper.getUser(userVO));
        Data<String> data = new Data<>("User updated");
        ResponseEntity<Data<String>> response = new ResponseEntity<>(data);

        return response;

    }


//    public List<User> load() {
//        // TODO: 6/15/2022 load data from file here
//
//        try{
//
//            FileReader reader = new FileReader("src/main/resources/users.json");
//            Type type = new TypeToken<List<User>>(){}.getType();
//            List<User> userList = this.gson.fromJson(reader, type);
//            if (userList==null){
//                userList = new ArrayList<>();
//            }
//            return userList;
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

//    public void saveToJson() {
//        try {
//            FileWriter fileWriter = new FileWriter("src/main/resources/users.json");
//            fileWriter.write(this.gson.toJson(repository.findAll(new UserCriteria())));
//            fileWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }


    @Override
    public ResponseEntity<Data<UserVO>> findById(@NonNull Long id) {

        Optional<User> userOptional = repository.findById(id);

        ResponseEntity<Data<UserVO>> response;
        if (userOptional.isPresent()){
            response = new ResponseEntity<>(new Data<>(mapper.getUserVO(userOptional.get())));
            return response;
        }
        ErrorVO errorVO = new ErrorVO("User not found", "User not found", 400);
        response = new ResponseEntity<>(new Data<>(errorVO));
        return response;
    }



    @Override
    public ResponseEntity<Data<List<UserVO>>> findAll(@NonNull UserCriteria criteria) {

        List<User> userList = repository.findAll(new UserCriteria()).get();

        List<UserVO> userVOS = new ArrayList<>();

        for (User user : userList) {
            userVOS.add(mapper.getUserVO(user));
        }

        ResponseEntity<Data<List<UserVO>>> listResponseEntity = new ResponseEntity<>(new Data<>(userVOS, userVOS.size()));

        return listResponseEntity;
    }

    public static UserService getInstance() {

        if (instance == null) {
            instance = new UserService(
                    ApplicationContextHolder.getBean(UserRepository.class),
                    ApplicationContextHolder.getBean(UserMapper.class));
        }
        return instance;
    }

    public ResponseEntity<Data<Void>> addOrgToUser(Long userId, Long orgId) {
        repository.addOrgToUser(userId,orgId);
        return new ResponseEntity<>(new Data<>(null));
    }

    public ResponseEntity<Data<List<UserVO>>> getUsersByIds(List<Long> userIds) {

        List<UserVO> users = new ArrayList<>();

        for (UserVO user : findAll(new UserCriteria()).getData().getBody()) {
            if(userIds.contains(user.getId()) && (!user.isDeleted() || !user.isBlocked())){
                users.add(user);
            }
        }

        return new ResponseEntity<>(new Data<>(users, users.size()));

    }
}
