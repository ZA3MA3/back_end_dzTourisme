package com.example.springJwt.Authentication.DTO;

import com.example.springJwt.Authentication.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequestDTO {
    private Integer id;
    private String username;
    private String editedUsername;
    private String email;
    private String password;
    private String confirmationPassword;
    private String   role;
    private String firstName;
    private String lastName;



}
