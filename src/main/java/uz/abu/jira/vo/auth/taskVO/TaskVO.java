package uz.muhammad.jira.vo.auth.taskVO;

import lombok.*;
import uz.muhammad.jira.domains.auth.Organization;
import uz.muhammad.jira.domains.auth.Task;
import uz.muhammad.jira.enums.TaskStatus;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  14:44 (Friday)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskVO extends GenericVO {
    private Long id;
    private String name;
    private List<Long> comments = new ArrayList<>();
    private List<Long> members = new ArrayList<>();
    private String createdAt;
    private Long createdBy;
    private String updatedAt;
    private Long updatedBy;
    private TaskStatus status;
    private boolean deleted;


}
