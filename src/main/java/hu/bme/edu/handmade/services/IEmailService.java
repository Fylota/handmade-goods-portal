package hu.bme.edu.handmade.services;

import org.springframework.http.ResponseEntity;

public interface IEmailService {
    ResponseEntity<?> sendPasswordReset(String userEmail, String token);
    ResponseEntity<?> sendNewsletterEmail();
}
