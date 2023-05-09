package hu.bme.edu.handmade.services;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface IEmailService {
    ResponseEntity<?> sendPasswordReset(String userEmail, String token);
    ResponseEntity<?> sendNewsletterEmail() throws IOException;
}
