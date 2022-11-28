package uz.muhammad.jira.vo.auth.projectVO;

import lombok.*;
import uz.muhammad.jira.domains.auth.Organization;
import uz.muhammad.jira.domains.auth.Project;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  14:43 (Friday)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectVO extends GenericVO {
    private Long id;
    private String name;
    private List<Long> columns = new ArrayList<>();
    private List<Long> members = new ArrayList<>();
    private String  createdAt;
    private Long createdBy;
    private String  updatedAt;
    private Long updatedBy;
    private String  deadline;
    private boolean blocked;
    private boolean deleted;

    public ProjectVO(Project project) {
        super(project.getId());
        this.name = project.getName();
        this.createdAt = project.getCreatedAt();
        this.createdBy = project.getCreatedBy();
    }

    @Builder(builderMethodName = "childBuilder")
    public ProjectVO(Long id, String name, String createdAt, Long createdBy) {
        super(id);
        this.name = name;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
