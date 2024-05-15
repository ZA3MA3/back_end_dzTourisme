package com.example.springJwt.CarRenal.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.springJwt.CarRenal.model.PickUpLocation;

@Repository
public interface PickUpLocationRepository extends JpaRepository<PickUpLocation, Integer> {

    List<PickUpLocation> findByNameContaining(String query);

    PickUpLocation findByName(String name);

    
}
