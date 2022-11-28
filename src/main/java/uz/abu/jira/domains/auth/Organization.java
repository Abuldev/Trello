package uz.muhammad.jira.domains.auth;

import lombok.*;
import uz.muhammad.jira.domains.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Team Developers
 * @project TrelloBY
 * @since 16/06/22   11:31   (Thursday)
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Organization implements BaseEntity {
    private Long id;
    private String name;
    private Long ownerId;
    private List<Long> projects = new ArrayList<>();
    private Map<Long, String> members = new HashMap<>();
    private String createdAt;
    private Long createdBy;
    private String updatedAt;
    private Long updatedBy;
    private boolean deleted = false;
    private boolean blocked = false;
}
