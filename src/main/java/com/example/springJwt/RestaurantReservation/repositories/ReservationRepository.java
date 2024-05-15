package com.example.springJwt.RestaurantReservation.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.RestaurantReservation.model.Reservation;
import com.example.springJwt.RestaurantReservation.model.Restaurant;


@Repository
public interface ReservationRepository  extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);
    


}
