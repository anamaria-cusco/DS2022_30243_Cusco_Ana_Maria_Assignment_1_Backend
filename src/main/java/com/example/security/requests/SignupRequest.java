package com.example.security.requests;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupRequest {
    private String username;
    private String password;
    private String name;
    private String email;
    private String address;
    private String role;
}


