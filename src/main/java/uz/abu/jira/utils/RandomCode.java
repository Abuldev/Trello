package uz.muhammad.jira.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



/**
 * @author Team <Developers>
 * @project Trello
 * @since 18/06/22   17:22   (Saturday)
 */

@Getter
@Setter
@NoArgsConstructor

public class RandomCode {
    private String randomCode;
    private String emailTo;
    private String emailFrom;
    private String sentTime;
}
