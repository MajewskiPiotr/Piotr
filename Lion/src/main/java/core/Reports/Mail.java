package core.Reports;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail  {

    public void send(String subject, String messageBody) {
        final String username = "piotrmajewski1983@wp.pl";
        final String password = "1983";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.wp.pl");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("piotrmajewski1983@wp.pl"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("dreamlab-demo@evercode.home.pl"));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }



}