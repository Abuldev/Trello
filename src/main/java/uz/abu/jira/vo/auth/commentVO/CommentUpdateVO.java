package uz.muhammad.jira.vo.auth.commentVO;

import lombok.*;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;

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
public class CommentUpdateVO extends GenericVO {
    private String  updatedAt;
    private Long updatedBy;
    private boolean blocked;
    private boolean deleted;
}
