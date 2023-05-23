package hu.bme.edu.handmade.web.dto.user;

import hu.bme.edu.handmade.validation.ValidEmail;
import hu.bme.edu.handmade.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    @ValidEmail
    String email;
    @ValidPassword
    String password;
}
