package com.example.springJwt.Authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springJwt.Authentication.model.User;

import ch.qos.logback.core.subst.Token;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    User findByTokens_Token(String token);




}
