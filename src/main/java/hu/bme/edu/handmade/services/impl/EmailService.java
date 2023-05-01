package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.models.EmailDetails;
import hu.bme.edu.handmade.services.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendSimpleMail(EmailDetails details) {
        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public ResponseEntity<?> sendPasswordReset(String userEmail, String token) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            String resetUrl = "http://localhost:4200/changePassword?token="+token;

            mailMessage.setFrom(sender);
            mailMessage.setTo(userEmail);
            mailMessage.setText("Please go to the following page to change your password: "+ resetUrl);
            mailMessage.setSubject("Password Reset");

            javaMailSender.send(mailMessage);
            return ResponseEntity.ok("Mail Sent Successfully...");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
