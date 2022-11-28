package uz.muhammad.jira.vo.auth.orgVO;

import lombok.*;
import uz.muhammad.jira.domains.auth.Project;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 16/06/22  15:02 (Thursday)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrgUpdateVO extends GenericVO {
    private String name;
    private List<Project> projectList;
    private String updatedAt;
    private Long updatedBy;
    private Boolean deleted;
    private Boolean blocked;
}
