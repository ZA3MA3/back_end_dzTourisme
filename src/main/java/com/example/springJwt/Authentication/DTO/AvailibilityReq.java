package com.example.springJwt.Authentication.DTO;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AvailibilityReq {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer hotelId;
}
