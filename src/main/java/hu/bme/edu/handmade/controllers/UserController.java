package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/me")
    public User user(Principal principal) {
        String name = principal.getName();
        return userService.findUserByEmail(name);
    }

    @GetMapping()
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @DeleteMapping(path = { "/{id}" })
    public User delete(@PathVariable("id") long id) {
        Optional<User> deletedUser = userService.getUserByID(id);
        deletedUser.ifPresent(u -> userService.deleteUser(u));
        return deletedUser.orElseGet(User::new);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") long id, @RequestBody UserDto user) {
        return userService.getUserByID(id)
                .map(foundUser -> userService.saveRegisteredUser(user, foundUser))
                .orElseGet(() -> userService.registerNewUserAccount(user));
    }
}
