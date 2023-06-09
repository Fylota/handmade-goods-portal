package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.request.ContactForm;
import hu.bme.edu.handmade.services.IEmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
public class EmailController {
    private final IEmailService emailService;

    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping("/sendNewsletter")
    public ResponseEntity<?> sendNewsletter(@RequestParam("fileId") Long fileId) {
        return emailService.sendNewsletterEmail(fileId);
    }

    @PostMapping("/contact")
    public ResponseEntity<?> sendContactUsMessage(@RequestBody ContactForm contactForm) {
        return emailService.sendContactUsEmail(contactForm);
    }
}
