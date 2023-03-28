package hu.bme.edu.handmade.web.dto.user;

import hu.bme.edu.handmade.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserDto {
    private String id;
    @NotNull
    private String firstName;
    private String lastName;
    @ValidEmail
    @NotNull
    private String email;
    private String phoneNumber;
    private List<AddressDto> addresses;
    public String getEmail() {
        return email;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public String getFirstName() {return firstName;}
    public void setFirstName(final String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(final String lastName) {this.lastName = lastName;}
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
    public List<AddressDto> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }
}
