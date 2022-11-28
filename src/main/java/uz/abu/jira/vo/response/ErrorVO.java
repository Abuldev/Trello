package uz.muhammad.jira.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Team <Developers>
 * @project TrelloBY
 * @since 16/06/22   11:31   (Thursday)
 */


@Setter
@Getter
@ToString
public class ErrorVO {
    private String friendlyMessage;
    private String developerMessage;
    private Integer status;
    private Timestamp timestamp;

    @Builder
    public ErrorVO(String friendlyMessage, String developerMessage, Integer status) {
        this.friendlyMessage = friendlyMessage;
        this.developerMessage = developerMessage;
        this.status = status;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}
