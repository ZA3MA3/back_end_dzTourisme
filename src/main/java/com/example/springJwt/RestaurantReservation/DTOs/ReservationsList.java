package com.example.springJwt.RestaurantReservation.DTOs;

import java.time.LocalTime;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ReservationsList {
    private Integer id;
    private int clients;
    private LocalDate reservationDate;
    private LocalTime time;   
    private Integer restaurantId;
    private String restaurantName;
    private int restaurantStars;
    private String restaurantCity;
    private Integer restaurantTimeTableId;
    private Integer userId;
}
