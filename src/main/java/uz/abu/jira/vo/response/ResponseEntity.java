package uz.muhammad.jira.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Team <Developers>
 * @project TrelloBY
 * @since 16/06/22   11:31   (Thursday)
 */


@Getter
@Setter
@ToString
public class ResponseEntity<T> {
    private T data;
    private Integer status;


    public ResponseEntity(T data) {
        this.data = data;
        this.status = 200;
    }

    public ResponseEntity(T data, Integer status) {
        this.data = data;
        this.status = status;
    }
}
