package com.example.springJwt.HotelBooking.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springJwt.HotelBooking.model.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

  /*@Query("SELECT DISTINCT r FROM Room r " +
    "LEFT JOIN FETCH r.roomType rt " +
    "LEFT JOIN r.bookings b " +
    "WHERE (r.isBooked = false OR (b is null OR (b.check_out < :checkInDate OR b.check_in > :checkOutDate))) " +
    "AND r.hotel.id = :hotelId " +  // Add this condition for the hotel ID
    "ORDER BY r.id") */ 

//     @Query("SELECT DISTINCT r FROM Room r " +
//     "LEFT JOIN FETCH r.roomType rt " +
//     "LEFT JOIN r.bookings b " +
//     "WHERE (r.isBooked = false OR b.id IS NULL OR (b.check_out < :checkInDate OR b.check_in > :checkOutDate)) " +
//      "AND r.hotel.id = :hotelId " +
//     "ORDER BY r.id") 
// List<Room> findRoomToBook(@Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate,  @Param("hotelId") Integer hotelId );

    List<Room> findByHotelIdAndIsBookedFalse(Integer hotelId);

    List<Room> findByHotelIdAndIsBookedTrue(Integer hotelId);

        
 }
