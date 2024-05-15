package com.example.springJwt.CarRenal.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.springJwt.CarRenal.model.Protection;

@Repository
public interface ProtectionRepository extends JpaRepository<Protection, Integer> {

    
}
