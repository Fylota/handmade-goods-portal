package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.models.Newsletter;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.models.request.ContactForm;
import hu.bme.edu.handmade.services.IEmailService;
import hu.bme.edu.handmade.services.INewsletterStorageService;
import hu.bme.edu.handmade.services.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final IUserService userService;
    private final INewsletterStorageService storageService;

    @Value("${spring.mail.username}") private String sender;

    public EmailService(JavaMailSender javaMailSender, IUserService userService, INewsletterStorageService storageService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.storageService = storageService;
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
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<?> sendNewsletterEmail(Long fileId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        Newsletter newsletter = storageService.load(fileId);
        String data = new String(newsletter.getContent(), StandardCharsets.UTF_8);

        List<User> recipients = userService.findUsersSubscribedToNewsletter();
        for (User user : recipients) {
            try {
                mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setTo(user.getEmail());
                mimeMessageHelper.setText(data, true);
                mimeMessageHelper.setSubject("Test newsletter for " + user.getFirstName());

                javaMailSender.send(mimeMessage);

            }
            catch (MessagingException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> sendContactUsEmail(ContactForm data) {
        if (!successfullySendAutomaticResponse(data.getEmail(), data.getBody())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(sender);
            mailMessage.setText("Sender: "+ data.getEmail() + "\n\n" + data.getBody());
            mailMessage.setSubject("Handmade Contact Us");

            javaMailSender.send(mailMessage);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean successfullySendAutomaticResponse(String recipient, String body) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setText(
                    "Thank you for contacting us! We will shortly respond to your message. \n\n" +
                    "Your message: \n\n" + body);
            mailMessage.setSubject("HandMade - AutoResponse - Do not reply");

            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
