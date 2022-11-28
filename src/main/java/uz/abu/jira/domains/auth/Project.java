package uz.muhammad.jira.domains.auth;

import lombok.*;
import uz.muhammad.jira.domains.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team <Developers>
 * @project TrelloBY
 * @since 16/06/22   11:31   (Thursday)
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Project implements BaseEntity {
    private Long id;
    private String name;
    private List<Long> columns = new ArrayList<>();
    private List<Long> members = new ArrayList<>();
    private String  createdAt;
    private Long createdBy;
    private String  updatedAt;
    private Long updatedBy;
    private String  deadline;
    private boolean blocked = false;
    private boolean deleted = false;
}
