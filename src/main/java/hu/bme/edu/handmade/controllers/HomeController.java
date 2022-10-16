package hu.bme.edu.handmade.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Greetings from Spring Boot!";
    }

}

