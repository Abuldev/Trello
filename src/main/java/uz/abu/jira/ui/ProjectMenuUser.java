package uz.muhammad.jira.ui;

import uz.muhammad.jira.configs.ApplicationContextHolder;
import uz.muhammad.jira.enums.MemberStatus;
import uz.muhammad.jira.mappers.ColumnMapper;
import uz.muhammad.jira.repository.auth.ColumnRepository;
import uz.muhammad.jira.services.auth.ColumnService;
import uz.muhammad.jira.utils.Tools;
import uz.muhammad.jira.utils.Writer;
import uz.muhammad.jira.vo.auth.columnVO.ColumnVO;
import uz.muhammad.jira.vo.auth.projectVO.ProjectVO;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 20/06/22   07:59   (Monday)
 */
public class ProjectMenuUser {

    private static ColumnService columnService = ApplicationContextHolder.getBean(ColumnService.class);

    public static void userMenu(ProjectVO projectVO) {

        Tools.clear();
        Tools.proInfo("", projectVO.getName());
        Tools.userInfo(Session.userName, MemberStatus.USER.name());




    }

    private static void allColumns(ProjectVO projectVO) {
        Tools.clear();
        Tools.userInfo(Session.userName, MemberStatus.MANAGER.name());

        for (ColumnVO columnVO : columnService.getColumnsByIds(projectVO.getColumns()).getData().getBody()) {
//            projectVO.get
        }

    }

}
