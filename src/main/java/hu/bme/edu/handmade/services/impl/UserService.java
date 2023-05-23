package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.exception.ResourceNotFoundException;
import hu.bme.edu.handmade.mappers.AddressMapper;
import hu.bme.edu.handmade.mappers.RoleMapper;
import hu.bme.edu.handmade.mappers.UserMapper;
import hu.bme.edu.handmade.models.Address;
import hu.bme.edu.handmade.models.PasswordResetToken;
import hu.bme.edu.handmade.models.Role;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.repositories.AddressRepository;
import hu.bme.edu.handmade.repositories.PasswordResetTokenRepository;
import hu.bme.edu.handmade.repositories.RoleRepository;
import hu.bme.edu.handmade.repositories.UserRepository;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.web.dto.user.AddressDto;
import hu.bme.edu.handmade.web.dto.user.RoleDto;
import hu.bme.edu.handmade.web.dto.user.UserDto;
import hu.bme.edu.handmade.exception.UserAlreadyExistException;
import hu.bme.edu.handmade.web.dto.user.UserRegistrationDto;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Transactional
@Validated
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AddressRepository addressRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public User registerNewUserAccount(UserRegistrationDto dto) {
        if (emailExists(dto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + dto.getEmail());
        }
        final User user = UserMapper.INSTANCE.toUserFromUserRegistrationDto(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        user.setEnabled(true);
        return userRepository.save(user);
    }
    @Override
    public Optional<User> updateUser(Long id, UserDto userToUpdate) {
        Optional<User> foundUser = userRepository.findById(id);
        foundUser.ifPresent(u -> {
            UserMapper.INSTANCE.updateUserFromDto(userToUpdate,u);
            userRepository.save(u);
        });
        return foundUser;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void setRoles(Long userId, List<Long> roleIds) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            List<Role> rolesToSet = (List<Role>) roleRepository.findAllById(roleIds);
            foundUser.get().setRoles(rolesToSet);
        }
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return RoleMapper.INSTANCE.toRoleDtos(roleRepository.findAll());
    }

    @Override
    public List<RoleDto> getUserRoles(Long userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            Collection<Role> roles = foundUser.get().getRoles();
            return RoleMapper.INSTANCE.toRoleDtos(roles);
        }
        return new ArrayList<>();
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
    public User getUserByID(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find user with id" + id));
    }

    @Override
    public Address addAddress(Long userId, AddressDto dto) {
        User foundUser = userRepository.findById(userId).orElseThrow();
        Address addr = addressRepository.save(AddressMapper.INSTANCE.toAddress(dto));
        addr.setUser(foundUser);
        foundUser.addAddress(addr);
        return addr;
    }

    @Override
    public Address updateAddress(Long userId, AddressDto dto) {
        return null;
    }

    @Override
    public User changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Token"));
        return isTokenExpired(passToken) ? "expired" : null;
    }

    @Override
    public User processOAuthPostLogin(String email, String lastName, String firstName) {
        if (emailExists(email)) {
            throw new UserAlreadyExistException("There is an account with that email address: " + email);
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        newUser.setEnabled(true);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(generatePassayPassword());

        return userRepository.save(newUser);
    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .map(PasswordResetToken::getUser)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find token"));
    }

    @Override
    public List<User> findUsersSubscribedToNewsletter() {
        return userRepository.findUsersBySubscribedToNewsletterTrue();
    }
    @Override
    public void setNewsletterSubscription(Long userId, boolean subscribe) {
        userRepository.findById(userId).ifPresent(user -> user.setSubscribedToNewsletter(subscribe));
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    public String generatePassayPassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "ERRONEOUS_SPECIAL_CHARS";
            }
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
