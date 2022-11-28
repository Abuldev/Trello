package uz.muhammad.jira.vo.auth.taskVO;

import lombok.*;
import uz.muhammad.jira.vo.BaseVO;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  14:44 (Friday)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TaskCreateVO implements BaseVO {
    private String name;
}
