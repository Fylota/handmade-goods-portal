package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.security.JwtRequest;
import hu.bme.edu.handmade.security.JwtResponse;
import hu.bme.edu.handmade.security.JwtTokenUtil;
import hu.bme.edu.handmade.services.impl.MyUserDetailsService;
import hu.bme.edu.handmade.services.impl.UserService;
import hu.bme.edu.handmade.web.dto.user.UserRegistrationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService;
    private final UserService userService;

    HomeController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                   MyUserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws AuthenticationException{

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register")
    public User saveUser(@RequestBody UserRegistrationDto user) {
        return userService.registerNewUserAccount(user);
    }

    private void authenticate(String username, String password) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }

}

