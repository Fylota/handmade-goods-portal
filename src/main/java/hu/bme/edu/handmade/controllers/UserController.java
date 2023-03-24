package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.mappers.AddressMapper;
import hu.bme.edu.handmade.mappers.UserMapper;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.web.dto.user.AddressDto;
import hu.bme.edu.handmade.web.dto.user.UserDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDto user(Principal principal) {
        String name = principal.getName();
        return UserMapper.INSTANCE.userToUserDto(userService.findUserByEmail(name));
    }

    @GetMapping("/{id}")
    public UserDto one(@PathVariable Long id) {
        User u = userService.getUserByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " is not found."));
        return UserMapper.INSTANCE.userToUserDto(u);
    }

    @GetMapping()
    public List<UserDto> getUsers() {
        return UserMapper.INSTANCE.usersToUserDtos(userService.findAllUsers());
    }

    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userToUpdate) {
        Optional<User> updatedUser = userService.updateUser(id, userToUpdate);

        return updatedUser
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> {
                    User createdUser = userService.registerNewUserAccount(UserMapper.INSTANCE.userDtoToRegistrationDto(userToUpdate));
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdUser.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(createdUser);
                });
    }
    @PostMapping("/{id}/addresses")
    public AddressDto addUserAddress(@PathVariable("id") Long userId, @RequestBody AddressDto address) {
        return AddressMapper.INSTANCE.toAddressDto(userService.addAddress(userId, address));
    }

    @PutMapping("/{id}/addresses/{addressId}")
    public AddressDto updateUserAddress(@PathVariable("id") Long userId, @PathVariable("addressId") Long addressId,@RequestBody AddressDto address) {
        return AddressMapper.INSTANCE.toAddressDto(userService.updateAddress(userId, address));
    }
}
