package hu.bme.edu.handmade.controllers;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
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
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

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

    @PostMapping("/auth/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("820575053600-723ifuvc2elm5rv5rr35br63gvoh5o73.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString.substring(1,idTokenString.length()-1));
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            if (userService.findUserByEmail(email) == null) {
                User newUser = userService.processOAuthPostLogin(email, familyName, givenName);
                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(newUser.getEmail());
                final String token = jwtTokenUtil.generateToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(token));
            }
            else {
                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(email);
                final String token = jwtTokenUtil.generateToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(token));
            }
        } else {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
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

