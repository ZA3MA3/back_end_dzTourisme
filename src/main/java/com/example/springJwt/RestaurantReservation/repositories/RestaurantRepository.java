package com.example.springJwt.RestaurantReservation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springJwt.RestaurantReservation.model.Restaurant;

@Repository
public interface RestaurantRepository  extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    List<Restaurant> findByNameContaining(String query);

    List<Restaurant> findByCity_CityNameContainingIgnoreCase(String query);

    


    
}
