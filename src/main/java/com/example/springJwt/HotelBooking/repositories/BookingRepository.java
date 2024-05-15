package com.example.springJwt.HotelBooking.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.HotelBooking.model.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByCheckInBetweenOrCheckOutBetween(LocalDate checkInDate, LocalDate checkOutDate,
            LocalDate checkInDate2, LocalDate checkOutDate2);

    List<Booking> findByUser(User user);




    
}