package com.example.springJwt.Authentication.repository;

import com.example.springJwt.Authentication.model.Token;
import com.example.springJwt.Authentication.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


    @Query("""
select t from Token t inner join User u on t.user.id = u.id
where t.user.id = :userId and t.loggedOut = false
""")
    List<Token> findAllTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);

    @Query("SELECT t.user.id FROM Token t WHERE t.loggedOut = false ORDER BY t.id DESC") 
       Long findUserIdByIsLoggedOutIsFalse();

    List<Token> findByUser(User user);
}
