package uz.muhammad.jira.vo.auth.projectVO;

import lombok.Getter;
import lombok.Setter;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22   18:18   (Friday)
 */
@Setter
@Getter
public class ProjectUpdateVO extends GenericVO {
    private String name;
    private String updatedAt;
    private Long updatedBy;
    private String deadline;
}
