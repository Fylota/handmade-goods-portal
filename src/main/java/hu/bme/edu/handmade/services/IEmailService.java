package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.request.ContactForm;
import org.springframework.http.ResponseEntity;

public interface IEmailService {
    ResponseEntity<?> sendPasswordReset(String userEmail, String token);
    ResponseEntity<?> sendNewsletterEmail(Long fileId);
    ResponseEntity<?> sendContactUsEmail(ContactForm data);
}
