package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.web.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

}
