package uz.muhammad.jira.domains.auth;

import lombok.*;
import uz.muhammad.jira.domains.BaseEntity;
import uz.muhammad.jira.enums.TaskStatus;

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
public class Task implements BaseEntity {
    private Long id;
    private String name;
    private List<Long> comments = new ArrayList<>();
    private List<Long> members = new ArrayList<>();
    private String  createdAt;
    private Long createdBy;
    private String  updatedAt;
    private Long updatedBy;
    private TaskStatus status;
    private boolean deleted;
}
