package com.example.springJwt.HotelBooking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springJwt.HotelBooking.model.Hotel;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    List<Hotel> findByNameContainingIgnoreCase(String name);

    List<Hotel> findByNameContaining(String query);

    List<Hotel> findByCity_CityNameContainingIgnoreCase(String query);



        
 }

