package com.galaxy.config;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
public class sendMailStatement {
    @Autowired
    private JavaMailSender mailSender;

    public static void main(String[] args) {
        // Recipient's email ID needs to be mentioned.
        String to = "@gmail.com";//change accordingly
            String from = "khus10316@gmail.com";//change accordingly
//            String host = "587";//or IP address
        final String username = "mohityadav4747@gmail.com";//change accordingly
        final String password = "mohit4747";//change accordingly
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Subject");

            // Now set the actual message
            message.setText("Hello, this is sample for to check send "
                    + "email using JavaMailAPI ");

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main1(String[] args) {

        sendMailStatement stmt=new sendMailStatement();

        stmt.sendMailWithAttachment( "mohityadav4747@gmail.com", "Monthly Bank Statement NICB",  "Dear Mohit \n Please find your monthly bank statement!",  "/Users/pradeepkumar/Desktop/statement/monthly_stmt_pdf/Sep_2023_Monthly_statement_7608.pdf");
    }
    public  void sendMailWithAttachment(String to, String subject, String body, String fileToAttach)
    {

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setFrom(new InternetAddress("myjava15797@gmail.com"));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);

            FileSystemResource file = new FileSystemResource(new File(fileToAttach));
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.addAttachment("/Users/pradeepkumar/Desktop/statement/monthly_stmt_pdf/images.jpeg", file);
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}