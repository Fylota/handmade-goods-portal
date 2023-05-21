package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.mappers.AddressMapper;
import hu.bme.edu.handmade.mappers.UserMapper;
import hu.bme.edu.handmade.models.CartProduct;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.services.IEmailService;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.services.IWishListService;
import hu.bme.edu.handmade.services.impl.CartProductService;
import hu.bme.edu.handmade.web.dto.CartProductDto;
import hu.bme.edu.handmade.web.dto.PasswordDto;
import hu.bme.edu.handmade.web.dto.ProductDto;
import hu.bme.edu.handmade.web.dto.user.AddressDto;
import hu.bme.edu.handmade.web.dto.user.RoleDto;
import hu.bme.edu.handmade.web.dto.user.UserDto;
import hu.bme.edu.handmade.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final CartProductService cartService;
    private final IWishListService wishListService;
    private final IEmailService emailService;

    UserController(IUserService userService, CartProductService cartService, IWishListService wishListService, IEmailService emailService) {
        this.userService = userService;
        this.cartService = cartService;
        this.wishListService = wishListService;
        this.emailService = emailService;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @GetMapping("/me")
    public UserDto user(Principal principal) {
        String name = principal.getName();
        return UserMapper.INSTANCE.userToUserDto(userService.findUserByEmail(name));
    }

    @SecurityRequirement(name = "Bearer_Authentication")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping()
    public List<UserDto> getUsers() {
        return UserMapper.INSTANCE.usersToUserDtos(userService.findAllUsers());
    }

    @SecurityRequirement(name = "Bearer_Authentication")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
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
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping("/{id}/addresses")
    public AddressDto addUserAddress(@PathVariable("id") Long userId, @RequestBody AddressDto address) {
        return AddressMapper.INSTANCE.toAddressDto(userService.addAddress(userId, address));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PutMapping("/{id}/addresses/{addressId}")
    public AddressDto updateUserAddress(@PathVariable("id") Long userId, @PathVariable("addressId") Long addressId,@RequestBody AddressDto address) {
        return AddressMapper.INSTANCE.toAddressDto(userService.updateAddress(userId, address));
    }

    /** Newsletter subscription */
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping("/{id}/unsubscribe")
    public void unsubscribeFromNewsletter(@PathVariable("id") Long userId) {
        userService.setNewsletterSubscription(userId, false);
    }

    /** Cart */
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @GetMapping("/{id}/cart")
    List<CartProduct> getCartProducts(@PathVariable("id") long userId) {
        return cartService.getCartProductsByUser(userId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping("/{id}/cart")
    CartProduct addCartProduct(@PathVariable("id") long userId, @RequestBody CartProductDto cartProductDto) {
        return cartService.addCartProduct(cartProductDto, userId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PutMapping("/{userId}/cart/{cartId}")
    CartProduct updateCartProduct(@PathVariable("userId") long userId, @PathVariable("cartId") long cartId, @RequestBody CartProductDto cartProductDto) {
        return cartService.findCartProductById(cartId)
                .map(product -> cartService.updateCartProduct(cartProductDto, cartId))
                .orElseGet(()->cartService.addCartProduct(cartProductDto, userId));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
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

    /** Wishlist */
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @GetMapping("/{user_id}/wishlist")
    List<ProductDto> getWishList(@PathVariable("user_id") Long id) {
        return wishListService.getWishListItemsByUserId(id);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping("/{user_id}/wishlist")
    ProductDto addToWishList(@PathVariable("user_id") Long userId, @RequestParam("product_id") Long productId) {
        return wishListService.addToWishlist(userId, productId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @DeleteMapping("/{user_id}/wishlist")
    void removeFromWishList(@PathVariable("user_id") Long userId, @RequestParam("product_id") Long productId) {
        wishListService.removeFromWishList(userId, productId);
    }

    /** Reset Password */
    @PostMapping("/sendPswResetRequest")
    public ResponseEntity<?> sendResetPasswordMail(@RequestParam("email") String userEmail) {
        String decodedEmail = URLDecoder.decode(userEmail, StandardCharsets.UTF_8);
        User user = userService.findUserByEmail(decodedEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User with email: " + userEmail + " is not found.");
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        return emailService.sendPasswordReset(decodedEmail, token);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<User> updatePassword(PasswordDto passwordDto) {
        String tokenError = userService.validatePasswordResetToken(passwordDto.getToken());
        if (tokenError != null) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        User updatedUser = userService.changeUserPassword(user, passwordDto.getNewPassword());
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping("/setRole")
    public void setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") List<Long> roleIds) {
        userService.setRoles(userId, roleIds);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @GetMapping("/roles")
    public List<RoleDto> getRoles() {
        return userService.getAllRoles();
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @GetMapping("/{userId}/roles")
    public List<RoleDto> getUserRoles(@PathVariable("userId") Long userId) {
        return userService.getUserRoles(userId);
    }
}
