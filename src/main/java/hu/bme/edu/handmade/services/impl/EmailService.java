package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.services.IEmailService;
import hu.bme.edu.handmade.services.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final IUserService userService;

    @Value("${spring.mail.username}") private String sender;

    public EmailService(JavaMailSender javaMailSender, IUserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
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
    public ResponseEntity<?> sendNewsletterEmail() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        List<User> recipients = userService.findUsersSubscribedToNewsletter();
        for (User user : recipients) {
            try {
                mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setTo(user.getEmail());
                mimeMessageHelper.setText("<h1>This is a test Spring Boot email</h1>" +
                        "<p>It can contain <strong>HTML</strong> content.</p>", true);
                mimeMessageHelper.setSubject("Test newsletter for " + user.getFirstName());

                javaMailSender.send(mimeMessage);

            }
            catch (MessagingException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }
}
