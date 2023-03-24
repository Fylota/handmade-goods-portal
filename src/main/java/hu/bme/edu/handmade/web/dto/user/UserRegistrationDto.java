package hu.bme.edu.handmade.web.dto.user;

import java.util.Objects;

public class UserRegistrationDto {
    String email;
    String firstName;
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationDto that = (UserRegistrationDto) o;
        return email.equals(that.email) && firstName.equals(that.firstName) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, password);
    }
}
