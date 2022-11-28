package uz.muhammad.jira.vo.auth.projectVO;

import lombok.*;
import uz.muhammad.jira.vo.BaseVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  14:43 (Friday)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ProjectCreateVO implements BaseVO {

    private String name;
    private Long createdBy;
    private String deadline;
    private List<Long> members = new ArrayList<>();
}
/**
 * private Long id;
 * private String name;
 * private List<Long> columns = new ArrayList<>();
 * private List<Long> members = new ArrayList<>();
 * private LocalDateTime createdAt;
 * private Long createdBy;
 * private LocalDateTime updatedAt;
 * private Long updatedBy;
 * private LocalDateTime deadline;
 * private boolean blocked;
 * private boolean deleted;
 */