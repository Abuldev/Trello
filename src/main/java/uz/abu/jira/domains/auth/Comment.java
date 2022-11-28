package uz.muhammad.jira.domains.auth;

import lombok.*;
import uz.muhammad.jira.domains.BaseEntity;

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
public class Comment implements BaseEntity {
    private Long id;
    private Long ownerId;
    private String  createdAt;
    private Long createdBy;
    private String  updatedAt;
    private Long updatedBy;
    private boolean blocked;
    private boolean deleted;
}
