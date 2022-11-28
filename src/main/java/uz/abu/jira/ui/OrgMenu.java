package uz.muhammad.jira.ui;

import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.criteria.UserCriteria;
import uz.muhammad.jira.enums.MemberStatus;
import uz.muhammad.jira.services.auth.OrgService;
import uz.muhammad.jira.services.auth.ProjectService;
import uz.muhammad.jira.services.auth.UserService;
import uz.muhammad.jira.utils.Color;
import uz.muhammad.jira.utils.Reader;
import uz.muhammad.jira.utils.Tools;
import uz.muhammad.jira.utils.Writer;
import uz.muhammad.jira.vo.auth.orgVO.OrgUpdateVO;
import uz.muhammad.jira.vo.auth.orgVO.OrgVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectCreateVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;
import uz.muhammad.jira.vo.response.Data;
import uz.muhammad.jira.vo.response.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 16/06/22   16:36   (Thursday)
 */
public class OrgMenu {

    private static UserService userService = ApplicationContextHolder.getBean(UserService.class);
    private static ProjectService projectService = ApplicationContextHolder.getBean(ProjectService.class);
    private static OrgService orgService = ApplicationContextHolder.getBean(OrgService.class);

    public static void showOrg(OrgVO org) {

        if (org.getMembers().get(Session.userId).equals(MemberStatus.ADMIN.name())) {
            showOrgAdminMenu(org);
        } else {
            showOrgUserMenu(org);
        }

    }

    private static void showOrgUserMenu(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName, org.getMembers().get(Session.userId));
        Writer.printlnMiddle("Projects -> 1", 80, ' ', Color.CYAN);
        Writer.printlnMiddle("Back     -> 0", 80, ' ', Color.RED);

        switch (Reader.readInt(" => ")) {
            case 0:
                return;
            case 1:
                projectsUser(org);
                break;
            default:
                Writer.printlnMiddle(" Wrong option", 80);
        }

        showOrgUserMenu(org);

    }

    private static void projectsUser(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        List<ProjectVO> projects = new ArrayList<>();
        for (ProjectVO project : projectService.getProjectsByIds(org.getProjects()).getData().getBody()) {
            if (project.getMembers().contains(Session.userId)) {
                projects.add(project);
            }
        }

        if (projects.size() == 0) {
            Writer.printlnMiddle("No projects", 80);
            Writer.printlnMiddle("0 -> Back", 80);
        }

        int order = 1;

        for (ProjectVO project : projects) {
            Writer.println("\t\t " + order + ") " + project.getName(), Color.GREEN);
        }

        int choice = Reader.readIntMiddle(" => ");

        if (choice == 0) return;

        if (projects.get(choice - 1).getMembers().indexOf(Session.userId) == 0) {
            ProjectMenuManager.projectMenuManager(org, projects.get(choice - 1));
            return;
        }
        ProjectMenuUser.userMenu(projects.get(choice - 1));
        return;
    }

    private static void showOrgAdminMenu(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName, org.getMembers().get(Session.userId));
        Writer.printlnMiddle("Projects -> 1", 80, ' ', Color.CYAN);
        Writer.printlnMiddle("Members  -> 2", 80, ' ', Color.CYAN);
        Writer.printlnMiddle("Settings -> 3", 80, ' ', Color.CYAN);
        Writer.printlnMiddle("Back     -> 0", 80, ' ', Color.RED);

        switch (Reader.readIntMiddle(" => ")) {
            case 0:
                return;
            case 1:
                projectsAdmin(org);
                break;
            case 2:
                members(org);
                break;
            case 3:
                settings(org);
                break;
            default:
                Writer.printlnMiddle(" Wrong option", 80);
        }

        showOrgAdminMenu(org);

    }

    private static void projectsAdmin(OrgVO org) {

        Tools.clear();
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        Tools.proInfo(org.getName());
        List<ProjectVO> projects = projectService.getProjectsByIds(org.getProjects()).getData().getBody();

        Writer.printlnMiddle("1 -> Add Project", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("2 -> Show all projects ", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("0 -> Back ", 80, ' ', Color.GREEN);

        int choice = Reader.readIntMiddle(" => ");
        if (choice == 0) return;
        else if (choice == 1) {
            addProject(org);
            projectsAdmin(org);
        } else
            ProjectMenuManager.managerMenu(org, projects);

    }

    private static void members(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        Tools.userInfo(Session.userName, org.getMembers().get(Session.userId));
        Writer.printlnMiddle("1. All Members", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("2. Add Member", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("0. Back", 80, ' ', Color.GREEN);

        switch (Reader.readIntMiddle(" => ")) {
            case 1 -> allMembers(org);
            case 2 -> addMember(org);
            case 0 -> showOrgAdminMenu(org);
            default -> Writer.printlnMiddle("Wrong option", 80);
        }

    }

    private static void settings(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        Writer.printlnMiddle(org.getName(), 80, ' ', Color.YELLOW);
        Writer.printlnMiddle("Organization Settings", 80, ' ', Color.YELLOW);
        Tools.userInfo(Session.userName, org.getMembers().get(Session.userId));

        Writer.printlnMiddle("1. Delete this Organization", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("2. Edit this Organization", 80, ' ', Color.GREEN);
        Writer.printlnMiddle("2. Show this Organization info", 80, ' ', Color.GREEN);

        switch (Reader.readIntMiddle(" => ")) {
            case 0:
                return;
            case 1:
                deleteThisOrg(org);
                break;
            case 2:
                editThisOrg(org);
                break;
            case 3:
                showThisOrg(org);
                break;
            default:
                Writer.printlnMiddle(" Wrong option", 80);
        }
        settings(org);
    }

    private static void addProject(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        ProjectCreateVO createVO = new ProjectCreateVO();
        createVO.setName(Reader.readLine(" Enter project name: "));
        createVO.setCreatedBy(Session.userId);

        int days = Reader.readInt(" How many days project will take: ");
        while (days < 1) {
            days = Reader.readInt(" How many days project will take: ");
        }

        createVO.setDeadline(LocalDateTime.now().plusDays(days).toString());

        Set<Long> userIds = org.getMembers().keySet();

        List<Long> list = userIds.stream().collect(Collectors.toList());

        List<UserVO> users = userService.getUsersByIds(list).getData().getBody();

        int order = 1;
        for (UserVO user : users) {
            Writer.printlnMiddle(order++ + ") " + user.getUserName(), 80, ' ', Color.GREEN);
        }

        Writer.printlnMiddle(" 0. Back ", 80, ' ', Color.GREEN);

        int choice = Reader.readInt(" Choose manager for your project: ");
        while (choice < 1) {
            choice = Reader.readInt(" Choose manager for your project: ");
        }

        Long id = users.get(choice - 1).getId();
        List<Long> members = createVO.getMembers();
        members.add(id);
        createVO.setMembers(members);

        ResponseEntity<Data<Long>> response = projectService.create(createVO);

        if (response.getData().isSuccess()) {

            orgService.addProjectToOrg(response.getData().getBody(), org.getId());
            return;
        }
        Writer.println(response.getData().getError());
        return;
    }

    private static void showThisOrg(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        Writer.printlnMiddle("Name: " + org.getName(), 80);
        Writer.printlnMiddle("Id: " + org.getId().toString(), 80);
        UserVO owner = userService.findById(org.getCreatedBy()).getData().getBody();
        Writer.printlnMiddle("Owner: " + owner.getUserName() + "(" + owner.getEmail() + ")", 80);
        Writer.printlnMiddle("CreatedAt: " + org.getCreatedAt().toString(), 80);
        Writer.printlnMiddle("CreatedAt: " + org.getCreatedAt().toString(), 80);
        Writer.printlnMiddle("UpdatedAt: " + org.getUpdatedAt().toString(), 80);
        Writer.printlnMiddle("UpdatedBy: " + org.getUpdatedBy().toString(), 80);
        Writer.printlnMiddle("Blocked: " + org.isBlocked(), 80);
        Reader.readLineMiddle("Press any key to back");
    }

    private static void editThisOrg(OrgVO org) {
        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        OrgUpdateVO dto = new OrgUpdateVO();
        String name = Reader.readLineMiddle("Enter new name: ");
        dto.setId(org.getId());
        dto.setName(name);
        dto.setUpdatedAt(LocalDateTime.now().toString());
        dto.setUpdatedBy(Session.userId);
        Writer.printlnMiddle(orgService.update(dto).getData().getBody(), 80);
        Reader.readLineMiddle("Press any key to back");
        return;
    }

    private static void deleteThisOrg(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        if (orgService.delete(org.getId()).getData().isSuccess()) {
            Writer.printlnMiddle(orgService.delete(org.getId()).getData().getBody(), 80);
        }
        Writer.printlnMiddle(orgService.delete(org.getId()).getData().getError().getFriendlyMessage(), 80);
        Writer.printlnMiddle(" Press any key to back", 80);
        Reader.readLineMiddle(" => ");
    }

    private static void addMember(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        Writer.printlnMiddle("Enter username (0 -> back)", 80);
        String username = Reader.readLineMiddle(" => ");
        if (username.equals("0")) return;
        for (UserVO userVO : userService.findAll(new UserCriteria()).getData().getBody()) {
            if (userVO.getUserName().equals(username)) {

                String yn = Reader.readLineMiddle(" Do you wanna add (Y/N)");
                if (yn.toLowerCase().startsWith("y")) {
                    if (orgService.addMemberToOrg(userVO.getId(), org.getId())) {
                        Tools.clear();
                        Writer.printlnMiddle("Member already added", 80);
                        return;
                    }
                } else {
                    return;
                }

            }
        }
        addMember(org);
    }

    private static void allMembers(OrgVO org) {

        Tools.clear();
        Tools.proInfo(org.getName());
        Tools.userInfo(Session.userName,org.getMembers().get(Session.userId));
        Set<Long> userIds = org.getMembers().keySet();

        List<Long> list = userIds.stream().collect(Collectors.toList());

        List<UserVO> users = userService.getUsersByIds(list).getData().getBody();

        int order = 1;
        Writer.printlnMiddle("All Members",80);
        for (UserVO user : users) {
            Writer.println("\t\t" + order + ") " + user.getUserName() + "\t\t\t role: " + org.getMembers().get(user.getId()));
        }
        Writer.println("\t\t 0. Back");
        int choice = Reader.readIntMiddle(" => ");

        if (choice == 0) {
            return;
        }

        memberSettings(users.get(choice - 1));

    }

    private static void memberSettings(UserVO userVO) {

    }
}
