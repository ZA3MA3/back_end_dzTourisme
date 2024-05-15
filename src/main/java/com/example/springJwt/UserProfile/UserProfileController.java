package com.example.springJwt.UserProfile;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springJwt.Authentication.DTO.AuthRequestDTO;
import com.example.springJwt.Authentication.model.Token;
import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.Authentication.repository.TokenRepository;
import com.example.springJwt.Authentication.repository.UserRepository;
import com.example.springJwt.Authentication.service.AuthenticationService;
import com.example.springJwt.Authentication.service.JwtService;
import com.example.springJwt.RestaurantReservation.DTOs.TokenDTO;

@RestController
public class UserProfileController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenRepository tokenRepository;

    @PostMapping("/getUserByToken/")
    public ResponseEntity<User> getUserByToken(@RequestBody TokenDTO token) {
        User user = userRepository.findByTokens_Token(token.getToken());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found when user is not found
        }
    }

    @PostMapping("/renewToken/{id}")
    public ResponseEntity<String> renewToken(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String jwt = jwtService.generateToken(user);
            authenticationService.revokeAllTokenByUser(user);
            authenticationService.saveUserToken(jwt, user);
            return ResponseEntity.ok(jwt);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PutMapping("/editUserProfile")
    public ResponseEntity<User> editUserProfile(@RequestBody AuthRequestDTO request) {
        Optional<User> optionalUser = userRepository.findById(request.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

          

            user.setUsername(request.getUsername());
            user.setFirstname(request.getFirstName());
            user.setLastname(request.getLastName());
            user.setEmail(request.getEmail());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            // User not found, return appropriate response
            return ResponseEntity.notFound().build();
        }
    }




}
