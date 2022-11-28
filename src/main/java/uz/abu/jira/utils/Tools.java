package uz.muhammad.jira.utils;

import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.UserCriteria;
import uz.muhammad.jira.services.auth.ColumnService;
import uz.muhammad.jira.services.auth.TaskService;
import uz.muhammad.jira.services.auth.UserService;
import uz.muhammad.jira.ui.MainMenu;
import uz.muhammad.jira.vo.auth.columnVO.ColumnVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectVO;
import uz.muhammad.jira.vo.auth.taskVO.TaskVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22   18:28   (Friday)
 */
public class Tools {

    private final static UserService userService = ApplicationContextHolder.getBean(UserService.class);
    private final static ColumnService columnService = ApplicationContextHolder.getBean(ColumnService.class);
    private final static TaskService taskService = ApplicationContextHolder.getBean(TaskService.class);

    public static void clear(){
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    public static boolean isUniqueEmail(String email) {

        ResponseEntity<Data<List<UserVO>>> all = userService.findAll(new UserCriteria());

        for (UserVO userVO : all.getData().getBody()) {
            if (userVO.getEmail().equals(email)){
                return false;
            }
        }

        return true;

    }

    public static void proInfo(String orgName){
        Writer.printlnRight("org: "+orgName, 80, ' ', Color.PURPLE);
        Writer.println("");
    }

    public static void proInfo(String orgName, String proName){
        Writer.printlnRight("org: "+orgName, 80, ' ', Color.PURPLE);
        Writer.printlnRight("pro: "+proName, 80, ' ', Color.PURPLE);
        Writer.println("");
    }

    public static void userInfo(String username){
        Writer.printlnRight("info: "+username, 80, ' ', Color.PURPLE);
        Writer.println("");
    }

    public static void userInfo(String username, String role){
        Writer.printlnRight(username+" (" + role + ")", 80, ' ', Color.PURPLE);
        Writer.println("");
    }
    public static Map<Long, AtomicLong> increments = new HashMap<>();

    public static Long getId(Long id) {
        if (increments.get(id)==null){
            increments.put(id, new AtomicLong(1));
        }
        return increments.get(id).getAndIncrement();
    }

    public static void projectsPrint(ProjectVO projectVO) {

        List<ColumnVO> columnVOS = columnService.getColumnsByIds(projectVO.getColumns()).getData().getBody();

        List<List<String>> lists = new ArrayList<>();

        List<String> list;

        for (ColumnVO columnVO : columnVOS) {
            list = new ArrayList<>();
            list.add(columnVO.getName());
            for (TaskVO taskVO : taskService.getTasksByIds(columnVO.getTasks()).getData().getBody()) {
                list.add(taskVO.getName());
            }
            lists.add(list);
        }
        int order = 1;
        for (List<String> stringList : lists) {
            Writer.printlnMiddles(stringList.toArray(new String[0]), 80, ' ', Color.GREEN);
        }

        if (lists.size()==0){
            Writer.printlnMiddle("You have no tasks", 80);
        }

        Writer.printlnMiddle("0. Back",80);
        int choice = Reader.readInt(" => ");
        if (choice==0) return;

    }
}
