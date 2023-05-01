package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.EmailDetails;
import org.springframework.http.ResponseEntity;

public interface IEmailService {
    String sendSimpleMail(EmailDetails details);
    ResponseEntity<?> sendPasswordReset(String userEmail, String token);
}
