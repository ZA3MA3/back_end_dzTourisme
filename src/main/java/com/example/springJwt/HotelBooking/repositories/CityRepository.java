package com.example.springJwt.HotelBooking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springJwt.HotelBooking.model.City;
import com.example.springJwt.RestaurantReservation.model.Restaurant;


@Repository
public interface CityRepository extends JpaRepository<City, Integer> {



    City findByCityNameIgnoreCase(String locationName);

    //City findByRestaurant(Restaurant restaurant);
    City findByRestaurants_Name(String restaurantName);

    List<City> findByCityNameContainingIgnoreCase(String query);


    
}
