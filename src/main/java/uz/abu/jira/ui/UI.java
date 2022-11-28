package uz.muhammad.jira.ui;

import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.UserCriteria;
import uz.muhammad.jira.repository.auth.*;
import uz.muhammad.jira.services.auth.UserService;
import uz.muhammad.jira.utils.*;
import uz.muhammad.jira.vo.auth.userVO.UserCreateVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static uz.muhammad.jira.utils.VerificationSender.createRandomCode;

/**
 * @author Team Developers
 * @project TrelloBY
 * @since 16/06/22   11:31   (Thursday)
 */

public class UI {

   // loading files

    private final static UserService userService = ApplicationContextHolder.getBean(UserService.class);

    static {
        File file = new File("src/main/resources/users.json");
        File file2 = new File("src/main/resources/tasks.json");
        File file3 = new File("src/main/resources/projects.json");
        File file4 = new File("src/main/resources/orgs.json");
        File file5 = new File("src/main/resources/comments.json");
        File file6 = new File("src/main/resources/columns.json");
        try {
            file.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
            file4.createNewFile();
            file5.createNewFile();
            file6.createNewFile();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserRepository.load();
        OrgRepository.load();
        ProjectRepository.load();
        ColumnRepository.load();
        TaskRepository.load();
        CommentRepository.load();
    }

    public static void main(String[] args) {

        Tools.clear();
        Writer.printlnMiddle("Trello helps teams move work forward.", 80,' ',Color.YELLOW);

        Writer.printlnMiddle("Sign up - it's free -> 1",80, ' ', Color.GREEN);
        Writer.printlnMiddle("Sign in             -> 2",80, ' ', Color.GREEN);
        Writer.printlnMiddle("What's Trello       -> 3",80, ' ', Color.GREEN);

        String choice = Reader.readLineMiddle(" => ");

        switch (choice){
            case "1" -> signUp();
            case "2" -> signIn();
            case "3" -> info();

        }
        main(args);
    }

    /**
     * UI method for creating user
     */
    private static void signUp() {

        Tools.clear();
        Writer.printlnMiddle(" \uD836\uDE4A▮ Trello ",80,' ',Color.GREEN);
        Writer.printlnMiddle(" Sign up for your account ",80,' ',Color.GREEN);

        String email = Reader.readLine("Email : ");
        while (!Tools.isUniqueEmail(email)){
            email = Reader.readLine("Enter Unique Email : ");
        }
        VerificationSender.emails.add(email);
        new ThreadHelp().start();

        Writer.print("Please enter verification code: ");
        String code = Reader.readLine();

        while (!code.equals(VerificationSender.randomCodes.get(email))) {
            Writer.print("Wrong verification code Retry:  ");
            code = Reader.readLine();
        }

        UserCreateVO.UserCreateVOBuilder builder = UserCreateVO.builder();
        builder.email(email);

        builder.userName(Reader.readLine("Username : "));
        builder.password(Reader.readLine("Password : "));
        UserCreateVO userCreateVO = builder.build();

        ResponseEntity<Data<Long>> responseData = userService.create(userCreateVO);
        if (responseData.getData().isSuccess()) {
            Tools.clear();
        } else {
            Writer.println(responseData.getData().getError(), Color.RED);
        }

    }


    private static void signIn() {

        Tools.clear();
        Writer.printlnMiddle(" \uD836\uDE4A▮ Trello ",80,' ',Color.GREEN);
        Writer.printlnMiddle(" Signing in ",80,' ',Color.GREEN);

        UserVO.UserVOBuilder builder = UserVO.childBuilder();
        builder.userName(Reader.readLine("Username : "));
        builder.password(Reader.readLine("Password : "));

        UserVO userVO = builder.build();
        ResponseEntity<Data<List<UserVO>>> responseData = userService.findAll(new UserCriteria());
        Data<List<UserVO>> data = responseData.getData();

        for (UserVO vo : data.getBody()) {

            if (vo.getUserName().equals(userVO.getUserName()) && vo.getPassword().equals(userVO.getPassword())) {
                Session.userName = vo.getUserName();
                Session.password = vo.getPassword();
                Session.userId = vo.getId();
                if (data.isSuccess()) {
                    MainMenu.control(); // Main menu
                    return;
                }
            }
        }

        Writer.println(data.getError(), Color.RED);

    }


    private static void info() {
        Writer.printlnMiddle("Trello makes it easier for teams to", 80,' ',Color.YELLOW);
        Writer.printlnMiddle("manage projects and tasks", 80,' ',Color.YELLOW);
        Writer.printlnMiddle("What is Trello? Trello is the visual tool that empowers your team to manage", 80,' ',Color.YELLOW);
        Writer.printlnMiddle("any type of project, workflow, or task tracking. Add files, checklists, or even", 80,' ',Color.YELLOW);
        Writer.printlnMiddle("automation: Customize it all for how your team works best. Just sign up,", 80,' ',Color.YELLOW);
        Writer.printlnMiddle("create a board, and you’re off!", 80,' ',Color.YELLOW);

        Writer.println("Sign up - it's free -> 1");
        Writer.println("Back                -> 2");


        String choice = new Scanner(System.in).next();
        switch (choice){
            case "1" : signUp(); break;
            case "0" : return;

        }


    }

    /**
     * UI method for getting user list
     */

//    private static void userList() {
//        ResponseEntity<Data<List<UserVO>>> responseData = userService.findAll(new UserCriteria());
//        Data<List<UserVO>> data = responseData.getData();
//        if (data.isSuccess()) {
//            Writer.println(responseData.getData(), Color.GREEN);
//        } else {
//            Writer.println(data.getError(), Color.RED);
//        }

//    }


}
