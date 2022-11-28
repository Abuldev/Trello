package uz.muhammad.jira.vo.auth.columnVO;

import lombok.*;
import uz.muhammad.jira.domains.auth.Task;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 16/06/22  15:02 (Thursday)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ColumnUpdateVO extends GenericVO {
    private String name;
    private List<Task> tasks;
    private String  updatedAt;
    private Long updatedBy;
    private boolean blocked;
    private boolean deleted;
}
