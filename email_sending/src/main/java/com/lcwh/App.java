package com.lcwh;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.*;

public class App {
    Session newSession=null;
    MimeMessage mimeMessage=null;

    public static void main(String args[]) throws AddressException, MessagingException, IOException{
        App mail = new App();
        mail.setupServerProperties();
        mail.draftEmail();
        mail.sendEmail();
    }

    private void sendEmail() throws MessagingException {
        String fromUser = "harshithrajtvj@gmail.com"; // Enter sender email id
        String fromUserPassword = "wpwf yazs inth wggt"; // Enter sender gmail password this will be authenticated by gmail smtp server
        String emailHost = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUser, fromUserPassword);
            }
        });

        Transport transport = session.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("Email successfully sent!!!");
    }

    private MimeMessage draftEmail() throws AddressException, MessagingException, IOException {
        String[] emailReceipients = {"harshithrajtv@gmail.com","harshithrajdrift1234@gmail.com"}; // Enter list of email recipients
        String emailSubject = "Test Mail";
        String emailBody = "Test Body of my email"; 
        mimeMessage = new MimeMessage(newSession);

        for (int i=0; i<emailReceipients.length; i++) {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients[i]));
        }

        mimeMessage.setSubject(emailSubject);

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailBody, "text/html"); // Changed content type to text/html
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        return mimeMessage;
    }

    private void setupServerProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        newSession = Session.getInstance(properties, null);
    }
}
