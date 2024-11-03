package companyUtil;

import java.util.logging.Level;
import java.util.logging.Logger;


public class EmailSender {

    private static final Logger logger = Logger.getLogger(EmailSender.class.getName());

    private EmailSender() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    public static void sendEmail(String customerEmail, String subject, String message){
        logger.log(Level.INFO, "Email to: {0}", customerEmail);
        logger.log(Level.INFO, "Subject: {0}", subject);
        logger.log(Level.INFO, "Body: ", message);
    }
}
