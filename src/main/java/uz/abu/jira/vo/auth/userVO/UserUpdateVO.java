package uz.muhammad.jira.vo.auth.userVO;

import lombok.*;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;

/**
 * @author Team <Developers>
 * @project TrelloBY
 * @since 16/06/22   11:31   (Thursday)
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserUpdateVO extends GenericVO {
    private String userName;
    private String password;
    private String updatedAt;
}
