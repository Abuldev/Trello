package uz.muhammad.jira.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import uz.muhammad.jira.configs.ApplicationContextHolder;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Team <Developers>
 * @project Trello
 * @since 18/06/22   16:57   (Saturday)
 */
public class VerificationSender {

    public static Map<String, String> randomCodes = new HashMap<>();
    public static List<RandomCode> randomCodesList = getFromJson();
    public static List<String> emails = new ArrayList<>();
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static String getRandomCode(){
        String random = RandomStringUtils.random(10, true, true);
        return random;
    }

    public static RandomCode createRandomCode() {

        RandomCode code = new RandomCode();

        String randomCode = getRandomCode();
        code.setRandomCode(randomCode);

        code.setEmailFrom("yusupovforwin@gmail.com");

        String email = emails.get(emails.size() - 1);
        code.setEmailTo(email);

        code.setSentTime(LocalDateTime.now().toString());

        randomCodes.put(email, randomCode);

        randomCodesList.add(code);
        writeThis();
        return code;
    }

    private static void writeThis() {

        {
            File file = new File("src/main/resources/randomCodes.json");
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        try {

            FileWriter fileWriter = new FileWriter("src/main/resources/randomCodes.json");
            Type type = new TypeToken<List<RandomCode>>() {}.getType();
            fileWriter.write(gson.toJson(randomCodesList,type));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static List<RandomCode> getFromJson() {
        try {
            FileReader fileReader = new FileReader("src/main/resources/randomCodes.json");
            Type type = new TypeToken<List<RandomCode>>() {}.getType();
            randomCodesList = gson.fromJson(fileReader,type);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (randomCodesList==null){
            randomCodesList = new ArrayList<>();
        }
        return randomCodesList;
    }

    public static String send() {

        Properties props = new Properties();
        try {
            props.load(new FileReader("src/main/resources/app.properties"));

            final String username = "yusupovforwin@gmail.com";
            final String password = "fbfycmnpaweudgqx";
            final String to = emails.get(emails.size()-1);

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject("Your verificatin code to Trello");
            String randomCode = randomCodes.get(emails.get(emails.size()-1));

            message.setText(randomCode);
            Transport.send(message);
            return randomCode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
}

