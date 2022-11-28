package uz.muhammad.jira.ui;

import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.enums.MemberStatus;
import uz.muhammad.jira.services.auth.ColumnService;
import uz.muhammad.jira.services.auth.OrgService;
import uz.muhammad.jira.services.auth.ProjectService;
import uz.muhammad.jira.services.auth.UserService;
import uz.muhammad.jira.utils.Color;
import uz.muhammad.jira.utils.Reader;
import uz.muhammad.jira.utils.Tools;
import uz.muhammad.jira.utils.Writer;
import uz.muhammad.jira.vo.auth.columnVO.ColumnCreateVO;
import uz.muhammad.jira.vo.auth.columnVO.ColumnVO;
import uz.muhammad.jira.vo.auth.orgVO.OrgVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectUpdateVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 19/06/22   14:58   (Sunday)
 */
public class ProjectMenuManager {

        private static UserService userService = ApplicationContextHolder.getBean(UserService.class);
        private static ProjectService projectService = ApplicationContextHolder.getBean(ProjectService.class);
        private static OrgService orgService = ApplicationContextHolder.getBean(OrgService.class);
        private static ColumnService columnService = ApplicationContextHolder.getBean(ColumnService.class);


    public static void managerMenu(OrgVO orgVO, List<ProjectVO> projectVOS) {

        Tools.clear();
        Tools.proInfo(orgVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());

        Writer.printlnMiddle("All Projects", 80);
        int order = 1;
        for (ProjectVO projectVO : projectVOS) {
            Writer.printlnMiddle(order++ + ") " + projectVO.getName(), 80, ' ', Color.GREEN);
        }
        Writer.printlnMiddle("0. Back", 80, ' ', Color.GREEN);
        int choice = Reader.readInt(" => ");
        if (choice == 0) return;
        while (choice < 1) {
            choice = Reader.readIntMiddle(" => ");
        }

        projectMenuManager(orgVO, projectVOS.get(choice - 1));

    }

    public static void projectMenuManager(OrgVO orgVO, ProjectVO projectVO) {

        Tools.clear();
        Tools.proInfo(orgVO.getName(), projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());

        Writer.printlnMiddle(projectVO.getName(), 80);

        Writer.printlnMiddle("1. Add Column", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("2. Settings", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("3. All Columns", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("0. Back", 80, ' ', Color.GREEN);
        Writer.println("");

        switch (Reader.readIntMiddle(" => ")) {
            case 0:
                return;
            case 1:
                ColumnMenu.addColumn(projectVO);
                break;
            case 2:
                settings(projectVO);
                break;
            case 3:
                ColumnMenu.allColumns(projectVO);
                break;
            default:
                Writer.printlnMiddle(" Wrong option", 80);
        }
        projectMenuManager(orgVO,projectVO);
    }



    private static void settings(ProjectVO projectVO) {
        Tools.clear();
        Tools.proInfo("", projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());
        Writer.printMiddle(projectVO.getName(), 80, ' ', Color.YELLOW);
        Writer.printlnMiddle("Project Settings", 80, ' ', Color.YELLOW);
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());

        Writer.printlnMiddle("1. Delete this Project", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("2. Edit this Project", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("3. Show this Project info", 80, ' ', Color.GREEN);

        switch (Reader.readIntMiddle(" => ")) {
            case 0:
                return;
            case 1:
                deleteThisProject(projectVO);
                break;
            case 2:
                editThisProject(projectVO);
                break;
            case 3:
                showThisProject(projectVO);
                break;
            default:
                Writer.printlnMiddle(" Wrong option", 80);
        }
        settings(projectVO);
    }



    private static void showThisProject(ProjectVO projectVO) {

        Tools.clear();
        Tools.proInfo("", projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());

        Writer.printlnMiddle("Name: " + projectVO.getName(), 80);
        Writer.printlnMiddle("Id: " + projectVO.getId().toString(), 80);
        UserVO owner = userService.findById(projectVO.getCreatedBy()).getData().getBody();
        Writer.printlnMiddle("Owner: " + owner.getUserName() + "(" + owner.getEmail() + ")", 80);
        Writer.printlnMiddle("CreatedAt: " + projectVO.getCreatedAt().toString(), 80);
        Writer.printlnMiddle("UpdatedBy: " + projectVO.getUpdatedBy().toString(), 80);
        Writer.printlnMiddle("UpdatedAt: " + projectVO.getUpdatedAt().toString(), 80);
        Writer.printlnMiddle("Deadline: " + projectVO.getDeadline().toString(), 80);


        Writer.printlnMiddle("All Columns ", 80);
        for (ProjectVO vo : projectService.getProjectsByIds(projectVO.getColumns()).getData().getBody()) {
            if (!vo.isDeleted())
                Writer.printlnMiddle(vo.getName() + "(" + vo.getDeadline() + ")", 80);
        }

        Writer.printlnMiddle("All Members ", 80);
        for (UserVO vo : userService.getUsersByIds(projectVO.getMembers()).getData().getBody()) {
            if (!vo.isDeleted())
                Writer.printlnMiddle(vo.getUserName() + "(" + vo.getEmail() + ")", 80);
        }

        Reader.readLineMiddle("Press any key to back");

}

    private static void editThisProject(ProjectVO projectVO) {
        Tools.clear();
        Tools.proInfo("", projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());
        ProjectUpdateVO dto = new ProjectUpdateVO();
        String name = Reader.readLineMiddle("Enter new name: ");
        dto.setId(projectVO.getId());
        dto.setName(name);
        dto.setUpdatedAt(LocalDateTime.now().toString());
        dto.setUpdatedBy(Session.userId);
        Writer.printlnMiddle(projectService.update(dto).getData().getBody(), 80);
        Reader.readLineMiddle("Press any key to back");
        return;
    }

    private static void deleteThisProject(ProjectVO projectVO) {
        projectService.delete(projectVO.getId());
    }

}
