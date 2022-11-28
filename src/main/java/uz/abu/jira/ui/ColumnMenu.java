package uz.muhammad.jira.ui;

import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.enums.MemberStatus;
import uz.muhammad.jira.services.auth.*;
import uz.muhammad.jira.utils.Reader;
import uz.muhammad.jira.utils.Tools;
import uz.muhammad.jira.utils.Writer;
import uz.muhammad.jira.vo.auth.columnVO.ColumnCreateVO;
import uz.muhammad.jira.vo.auth.columnVO.ColumnUpdateVO;
import uz.muhammad.jira.vo.auth.columnVO.ColumnVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectVO;
import uz.muhammad.jira.vo.auth.taskVO.TaskVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 20/06/22   12:10   (Monday)
 */
public class ColumnMenu {

    private static UserService userService = ApplicationContextHolder.getBean(UserService.class);
    private static ProjectService projectService = ApplicationContextHolder.getBean(ProjectService.class);
    private static OrgService orgService = ApplicationContextHolder.getBean(OrgService.class);
    private static ColumnService columnService = ApplicationContextHolder.getBean(ColumnService.class);
    private static TaskService taskService = ApplicationContextHolder.getBean(TaskService.class);


    public static void addColumn(ProjectVO projectVO) {

        Tools.clear();
        Tools.proInfo("", projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());
        ColumnCreateVO createVO = new ColumnCreateVO();
        createVO.setName(Reader.readLine("Enter column name: "));
        createVO.setCreatedBy(Session.userId);

        ResponseEntity<Data<Long>> response = columnService.create(createVO);
        if (response.getData().isSuccess()) {
            projectService.addColToPro(response.getData().getBody(), projectVO.getId());
            return;
        }
        Writer.println(response.getData().getError());
        return;
    }

    public static void allColumns(ProjectVO projectVO) {
        Tools.clear();
        Tools.proInfo("", projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());
        Writer.printlnMiddle("All Columns", 80);
        int order = 1;

        List<ColumnVO> columnVOS = columnService.getColumnsByIds(projectVO.getColumns()).getData().getBody();

        for (ColumnVO columnVO : columnVOS) {
            Writer.printlnMiddle(order + ") " + columnVO.getName(), 80);
            order++;
        }

        Writer.printlnMiddle("0. Back", 80);
        int choice = Reader.readIntMiddle(" => ");

        if (choice == 0) return;
        if (projectVO.getMembers().indexOf(Session.userId) == 0) {
            showThisColumnAdmin(projectVO, columnVOS.get(choice - 1));
        } else {
            showThisColumn(projectVO, columnVOS.get(choice - 1));
        }

    }

    private static void showThisColumnAdmin(ProjectVO projectVO, ColumnVO columnVO) {
        Tools.clear();
        Tools.proInfo("", projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());
        Writer.printlnMiddle("1. Settings", 80);
        Writer.printlnMiddle("2. Show this", 80);
        Writer.printlnMiddle("0. Back ", 80);

        switch (Reader.readIntMiddle(" => ")) {
            case 1 -> settings(columnVO);
            case 2 -> showThisColumn(projectVO, columnVO);
            case 0 -> allColumns(projectVO);
            default -> Writer.printlnMiddle("Wrong option", 80);
        }
        showThisColumnAdmin(projectVO, columnVO);
    }

    private static void settings(ColumnVO columnVO) {
        Tools.clear();

        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());

        Writer.printlnMiddle("1. Delete column", 80);
        Writer.printlnMiddle("2. Edit column", 80);
        Writer.printlnMiddle("0. Back ", 80);

        switch (Reader.readIntMiddle(" => ")) {
            case 1: {
                columnService.delete(columnVO.getId());
                break;
            }
            case 2: {
                ColumnUpdateVO updateVO = new ColumnUpdateVO();
                updateVO.setId(columnVO.getId());
                updateVO.setName(Reader.readLineMiddle(" Enter new column name: "));
                columnService.update(updateVO);
                break;
            }
            case 0:
                return;
            default:
                Writer.printlnMiddle("Wrong option", 80);
                break;
        }

    }

    private static void showThisColumn(ProjectVO projectVO, ColumnVO columnVO) {

        Tools.clear();
        Tools.proInfo("", projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());
        List<TaskVO> tasks = taskService.getTasksByIds(columnVO.getTasks()).getData().getBody();

        Writer.printlnMiddle(columnVO.getName(), 80);
        String text;
        int order = 1;
        for (TaskVO task : tasks) {
            text = "" + order + ") " + task.getName() + "";
            order++;
            Writer.printlnMiddle(text, 80);
        }

        Writer.printlnMiddle("0. Back", 80);
        int choice = Reader.readIntMiddle(" => ");

        if (choice == 0) return;

        showThisTask(columnVO, tasks.get(choice - 1));

    }

    private static void showThisTask(ColumnVO columnVO, TaskVO taskVO) {

    }
}
