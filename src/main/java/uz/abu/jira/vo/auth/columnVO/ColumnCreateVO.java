package uz.muhammad.jira.vo.auth.columnVO;

import lombok.*;
import uz.muhammad.jira.vo.BaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 16/06/22   15:58   (Thursday)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ColumnCreateVO implements BaseVO {
    private Long id;
    private String name;
    private Long createdBy;
}
