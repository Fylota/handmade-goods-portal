package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.EmailDetails;
import hu.bme.edu.handmade.services.IEmailService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
public class EmailController {
    private final IEmailService emailService;

    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        return emailService.sendSimpleMail(details);
    }
}
