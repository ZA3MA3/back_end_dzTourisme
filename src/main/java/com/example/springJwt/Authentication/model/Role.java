package com.example.springJwt.Authentication.model;

import java.util.List;
import java.util.Set;



import org.springframework.security.core.authority.SimpleGrantedAuthority;



import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER,
    ADMIN


}



