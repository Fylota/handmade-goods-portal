package hu.bme.edu.handmade.web.dto;

import hu.bme.edu.handmade.validation.ValidEmail;
import hu.bme.edu.handmade.validation.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
    private String id;
    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;
    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;
    @ValidPassword
    private String password;
    @NotNull
    @Size(min = 1)
    private String matchingPassword;
    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;
    private String phoneNumber;
    private String address;
    private String encryptedPassword;
    public String getEncryptedPassword() {
        return encryptedPassword;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
    private Integer role;
    public String getEmail() {
        return email;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public Integer getRole() {
        return role;
    }
    public void setRole(final Integer role) {
        this.role = role;
    }
    public String getFirstName() {return firstName;}
    public void setFirstName(final String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(final String lastName) {this.lastName = lastName;}
    public String getPassword() {return password;}
    public void setPassword(final String password) {this.password = password;}
    public String getMatchingPassword() {return matchingPassword;}
    public void setMatchingPassword(final String matchingPassword) {this.matchingPassword = matchingPassword;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
