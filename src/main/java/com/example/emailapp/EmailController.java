package com.example.emailapp;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@RestController
public class EmailController {

    @GetMapping("/sendemail")
    public String sendEmail() {
        try {
            sendMail();
            return "✅ Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Failed to send email: " + e.getMessage();
        }
    }

    private void sendMail() throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        final String senderEmail = "ar9204@srmist.edu.in";
        final String appPassword = "ywew zbbq plnn jcjj"; // Replace this

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        // Enable debugging (optional, helps find issues)
        session.setDebug(true);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderEmail, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ar9204@srmist.edu.in"));
        msg.setSubject("Testing Spring Boot Email");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("This is a test email sent using Spring Boot & Gmail SMTP.", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Optional: Add attachment (use actual file path)
         MimeBodyPart attachPart = new MimeBodyPart();
         attachPart.attachFile("D:\\SpringBoot\\FourBoot\\pom.xml");
         multipart.addBodyPart(attachPart);

        msg.setContent(multipart);

        Transport.send(msg);
    }
}
