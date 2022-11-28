package uz.muhammad.jira.utils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 18/06/22   17:18   (Saturday)
 */
public class ThreadHelp extends Thread{
    @Override
    public void run() {
        VerificationSender.createRandomCode();
//        VerificationSender.send();
    }


}
