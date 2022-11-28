package uz.muhammad.jira.vo.auth.taskVO;

import lombok.*;
import uz.muhammad.jira.domains.auth.Comment;
import uz.muhammad.jira.domains.auth.Member;
import uz.muhammad.jira.enums.TaskStatus;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;
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

public class TaskUpdateVO  extends GenericVO {
    private String name;
    private List<Comment> comments;
    private List<Member> members;
    private String updatedAt;
    private Long updatedBy;
    private TaskStatus status;
    private boolean deleted;
}
