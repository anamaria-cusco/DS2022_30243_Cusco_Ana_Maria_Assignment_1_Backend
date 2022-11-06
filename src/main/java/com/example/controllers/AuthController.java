package com.example.controllers;

import com.example.UrlMapping;

import com.example.dtos.UserDTO;
import com.example.dtos.UserDetailsImpl;
import com.example.dtos.builders.UserBuilder;
import com.example.security.JwtTokenUtil;
import com.example.security.requests.JwtResponse;
import com.example.security.requests.LoginRequest;
import com.example.security.requests.SignupRequest;
import com.example.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;



@RestController
@CrossOrigin
@RequestMapping(UrlMapping.AUTH)
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(UrlMapping.SIGN_IN)
    public  ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {

        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    );

            UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
            String jwt = jwtTokenUtil.generateAccessToken(userDetails);
            LOGGER.error(userDetails.getAuthorities().toString());
            return ResponseEntity.ok(
                    JwtResponse.builder()
                            .token(jwt)
                            .id(userDetails.getUser().getId())
                            .username(userDetails.getUsername())
                            .email(userDetails.getUser().getEmail())
                            .role(userDetails.getUser().getRole())
                            .build()
            );

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(UrlMapping.SIGN_UP)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        LOGGER.info("sing-up");
        if (authService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (authService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        authService.register(signUpRequest);


        return ResponseEntity.ok("User registered successfully!");
    }



}
