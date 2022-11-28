package uz.muhammad.jira.mappers;

import uz.muhammad.jira.domains.auth.Column;
import uz.muhammad.jira.domains.auth.User;
import uz.muhammad.jira.vo.auth.columnVO.ColumnVO;
import uz.muhammad.jira.vo.auth.userVO.UserVO;

import java.time.LocalDateTime;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 17/06/22  15:23 (Friday)
 */
public class ColumnMapper implements BaseMapper {

    private static ColumnMapper instance;

    public static ColumnMapper getInstance() {
        if (instance==null){
            instance = new ColumnMapper();
        }
        return instance;
    }

    public ColumnVO getColumnVo(Column column) {
        ColumnVO columnVO = new ColumnVO();

        columnVO.setId(columnVO.getId());
        columnVO.setName(column.getName());
        columnVO.setTasks(column.getTasks());
        columnVO.setBlocked(column.isBlocked());
        columnVO.setDeleted(column.isDeleted());
        columnVO.setCreatedAt(column.getCreatedAt());
        columnVO.setCreatedBy(column.getCreatedBy());
        columnVO.setUpdatedAt(column.getUpdatedAt());
        columnVO.setUpdatedBy(column.getUpdatedBy());

        return columnVO;
    }
//    public static Column getColumn(ColumnVO columnVO){
//        Column column = new Column();
//        column.setName(columnVO.getName());
//        column.setId(System.currentTimeMillis());
//        column.setCreatedAt(LocalDateTime.now());
//        column.setCreatedBy(columnVO.getId());
//
//        return column;
//    }
}