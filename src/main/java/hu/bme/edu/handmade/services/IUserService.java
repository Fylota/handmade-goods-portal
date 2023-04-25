package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Address;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.web.dto.user.AddressDto;
import hu.bme.edu.handmade.web.dto.user.UserDto;
import hu.bme.edu.handmade.web.dto.user.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User registerNewUserAccount(UserRegistrationDto dto);
    Optional<User> updateUser(Long id, UserDto userToUpdate);
    void deleteUser(Long userId);
    User findUserByEmail(String email);
    List<User> findAllUsers();
    Optional<User> getUserByID(long id);
    Address addAddress(Long userId, AddressDto dto);
    Address updateAddress(Long userId, AddressDto dto);
    void changeUserPassword(User user, String password);
    boolean checkIfValidOldPassword(User user, String password);
    void processOAuthPostLogin(String email);
}
