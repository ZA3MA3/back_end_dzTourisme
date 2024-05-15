package com.example.springJwt.CarRenal.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.CarRenal.DTOs.RentalsList;
import com.example.springJwt.CarRenal.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

    // List<Rental> findAllByCar_Cities_CityName(String cityName);

    List<Rental> findAllByCar_Locations_Name(String locationName);

    List<Rental> findByUser(User user);

    
}
