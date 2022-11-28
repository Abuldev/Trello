package uz.muhammad.jira.domains.auth;

import lombok.*;
import uz.muhammad.jira.domains.BaseEntity;
import uz.muhammad.jira.enums.MemberStatus;

import java.time.LocalDateTime;

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
public class Member implements BaseEntity {
    private Long id;
    private Long userId;
    private Long organizationId;
    private Long projectId;
    private MemberStatus status;
    private boolean blocked;
    private boolean deleted;
}
