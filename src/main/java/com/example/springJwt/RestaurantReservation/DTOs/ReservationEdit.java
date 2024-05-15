package com.example.springJwt.RestaurantReservation.DTOs;


import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ReservationEdit {
    

   private Integer reservationId; 
   private int clients;
   private LocalDate reservationDate;
   private LocalTime time;
   private String firstName;
   private String lastName;
   private String phoneNumber;
   private Integer timeTableId;
   private Integer restaurantId;
   private Integer userId;
   
}
