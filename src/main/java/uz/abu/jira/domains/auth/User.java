package uz.muhammad.jira.domains.auth;

import lombok.*;
import uz.muhammad.jira.domains.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team Developers
 * @project TrelloBY
 * @since 16/06/22   11:31   (Thursday)
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements BaseEntity {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private List<Long> organizations = new ArrayList<>();
    private String createdAt;
    private String updatedAt;
    private boolean deleted;
    private boolean blocked;


    public void addOrganization(Long orgId) {
        organizations.add(orgId);
    }
}
