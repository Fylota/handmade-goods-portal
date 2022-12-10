package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.model_assemblers.UserModelAssembler;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.web.dto.UserDto;
import hu.bme.edu.handmade.web.dto.error.UserNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final UserModelAssembler assembler;

    UserController(IUserService userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @GetMapping("/me")
    public EntityModel<User> user(Principal principal) {
        String name = principal.getName();
        User user = userService.findUserByEmail(name);
        return assembler.toModel(user);
    }

    @GetMapping("/{id}")
    public EntityModel<User> one(@PathVariable Long id) {
        User user = userService.getUserByID(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " is not found."));

        return assembler.toModel(user);
    }

    @GetMapping()
    public CollectionModel<EntityModel<User>> getUsers() {
        List<User> users = userService.findAllUsers();
        return assembler.toCollectionModel(users);
    }

    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<User> deletedUser = userService.getUserByID(id);
        deletedUser.ifPresent(userService::deleteUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDto user) {
        User updatedUser = userService.getUserByID(id)
                .map(foundUser -> userService.saveRegisteredUser(user, foundUser))
                .orElseGet(() -> userService.registerNewUserAccount(user));

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
