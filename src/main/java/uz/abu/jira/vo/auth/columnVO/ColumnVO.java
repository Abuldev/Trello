package uz.muhammad.jira.vo.auth.columnVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.muhammad.jira.domains.auth.Column;
import uz.muhammad.jira.vo.GenericVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 16/06/22   15:59   (Thursday)
 */
@Getter
@Setter

public class ColumnVO extends GenericVO {
    private Long id;
    private String name;
    private List<Long> tasks =new ArrayList<>();
    private String createdAt;
    private Long createdBy;
    private String updatedAt;
    private Long updatedBy;
    private boolean blocked;
    private boolean deleted;

}
