package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.repositories.RoleRepository;
import hu.bme.edu.handmade.repositories.UserRepository;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.web.dto.UserDto;
import hu.bme.edu.handmade.web.dto.error.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User registerNewUserAccount(UserDto accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        user.setEnabled(true);
        return userRepository.save(user);
    }
    @Override
    public User saveRegisteredUser(UserDto newUser, User foundUser) {
        foundUser.setPhoneNumber(newUser.getPhoneNumber());
        foundUser.setEmail(newUser.getEmail());
        foundUser.setAddress(newUser.getAddress());
        foundUser.setFirstName(newUser.getFirstName());
        foundUser.setLastName(newUser.getLastName());
        return userRepository.save(foundUser);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByID(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
}
