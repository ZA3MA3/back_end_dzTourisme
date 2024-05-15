package com.example.springJwt.CarRenal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springJwt.CarRenal.model.EngineType;

@Repository
public interface EngineTypeRepository extends JpaRepository<EngineType, Integer> {
    
    EngineType findByType(String type);

}
