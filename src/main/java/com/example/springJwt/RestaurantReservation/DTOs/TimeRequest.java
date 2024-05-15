package com.example.springJwt.RestaurantReservation.DTOs;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TimeRequest {

    private Integer restaurantId;
    private LocalDate reservationDate;
    private LocalTime hour;

   

}
