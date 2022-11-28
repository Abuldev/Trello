package uz.muhammad.jira.mappers;

import uz.muhammad.jira.domains.auth.Project;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.vo.auth.projectVO.ProjectVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;

import java.time.LocalDateTime;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  15:22 (Friday)
 */
public class ProjectMapper implements BaseMapper {

    private static ProjectMapper instance;

    public static Project getProject(ProjectVO projectVO){
        Project project = new Project();
        project.setName(projectVO.getName());
        project.setId(System.currentTimeMillis());
        project.setCreatedAt(LocalDateTime.now().toString());
        project.setCreatedBy(projectVO.getId());

        return project;
    }

    public static ProjectMapper getInstance() {

        if (instance == null){
        instance = new ProjectMapper();
    }
    return instance;
    }

    public ProjectVO getProjectVo(Project project) {

        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(project.getId());
        projectVO.setName(project.getName());
        projectVO.setDeadline(project.getDeadline());
        projectVO.setCreatedBy(project.getCreatedBy());
        projectVO.setCreatedAt(project.getCreatedAt());
        projectVO.setBlocked(project.isBlocked());
        projectVO.setDeleted(project.isDeleted());
        projectVO.setColumns(project.getColumns());
        projectVO.setMembers(project.getMembers());
        return projectVO;

    }
}
