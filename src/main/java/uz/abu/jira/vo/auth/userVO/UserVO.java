package uz.muhammad.jira.vo.auth.userVO;

import lombok.*;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class UserVO extends GenericVO {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private List<Long> organizations = new ArrayList<>();
    private String createdAt;
    private String updatedAt;
    private boolean deleted;
    private boolean blocked;

    public UserVO(User user) {
        super(user.getId());
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.createdAt = user.getCreatedAt();
    }

    @Builder(builderMethodName = "childBuilder")
    public UserVO(Long id, String userName, String password, String createdAt) {
        super(id);
        this.userName = userName;
        this.password = password;
        this.createdAt = createdAt;
    }
}
