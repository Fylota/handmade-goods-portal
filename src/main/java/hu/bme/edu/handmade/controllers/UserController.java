package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.mappers.AddressMapper;
import hu.bme.edu.handmade.mappers.UserMapper;
import hu.bme.edu.handmade.models.CartProduct;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.services.impl.CartProductService;
import hu.bme.edu.handmade.web.dto.CartProductDto;
import hu.bme.edu.handmade.web.dto.user.AddressDto;
import hu.bme.edu.handmade.web.dto.user.UserDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    private final CartProductService cartService;

    UserController(IUserService userService, CartProductService cartService) {
        this.userService = userService;
        this.cartService = cartService;
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

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping()
    public List<UserDto> getUsers() {
        return UserMapper.INSTANCE.usersToUserDtos(userService.findAllUsers());
    }

    @Operation(summary = "Delete user", description = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
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

    @GetMapping("/{id}/cart")
    List<CartProduct> getCartProducts(@PathVariable("id") long userId) {
        return cartService.getCartProductsByUser(userId);
    }

    @PostMapping("/{id}/cart")
    CartProduct addCartProduct(@PathVariable("id") long userId, @RequestBody CartProductDto cartProductDto) {
        return cartService.addCartProduct(cartProductDto, userId);
    }

    @PutMapping("/{userId}/cart/{cartId}")
    CartProduct updateCartProduct(@PathVariable("userId") long userId, @PathVariable("cartId") long cartId, @RequestBody CartProductDto cartProductDto) {
        return cartService.findCartProductById(cartId)
                .map(product -> cartService.updateCartProduct(cartProductDto, cartId))
                .orElseGet(()->cartService.addCartProduct(cartProductDto, userId));
    }

    @DeleteMapping("/{userId}/cart/{cartId}")
    ResponseEntity<?> removeCartProduct(@PathVariable("userId") Long userId, @PathVariable("cartId") Long cartId) {
        try {
            cartService.deleteCartProduct(cartId);
            return ResponseEntity.noContent().build();
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
