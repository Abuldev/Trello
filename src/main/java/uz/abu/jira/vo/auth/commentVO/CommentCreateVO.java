package uz.muhammad.jira.vo.auth.commentVO;

import lombok.*;
import uz.muhammad.jira.vo.BaseVO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCreateVO implements BaseVO {
    private Long ownerId;
}
