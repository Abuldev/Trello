package uz.muhammad.jira.vo.response;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Team <Developers>
 * @project TrelloBY
 * @since 16/06/22   11:31   (Thursday)
 */


@Getter
@ToString
public class Data<T> {
    private T body;
    private ErrorVO error;
    private Integer total;

    private boolean success;


    public Data(T body) {
        this.body = body;
        this.success = true;
    }

    public Data(T body, Integer total) {
        this.body = body;
        this.total = total;
        this.success = true;
    }

    public Data(ErrorVO error) {
        this.error = error;
        this.success = false;
    }
    
}
