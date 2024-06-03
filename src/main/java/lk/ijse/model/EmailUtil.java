package lk.ijse.model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    public static void sendEmail(String recipient, String subject, String body) throws MessagingException {
        String username = "sherulfernando11@gmail.com"; // Replace with my email
        String password = "zrmi owfc cznu tbju"; // Replace with your password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");//Enables SMTP authentication.
        props.put("mail.smtp.starttls.enable", "true");// Enables STARTTLS encryption.
        props.put("mail.smtp.host", "smtp.gmail.com"); //  Sets the SMTP server to Gmail's.
        props.put("mail.smtp.port", "587"); //  SMTP port

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });// Creates a mail session with the specified properties and authenticator.Authernticator absract class eken hadila thiyana annonymous inner class ekak

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username)); //Sets the sender's email address.
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); //Sets the recipient's email address.
        message.setSubject(subject); //Sets the email's subject.
        message.setText(body); // Sets the email's body text.

        Transport.send(message); //Sends the email.
        System.out.println("Email sent successfully!");
    }
}
