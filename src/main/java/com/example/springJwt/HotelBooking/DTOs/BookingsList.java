package com.example.springJwt.HotelBooking.DTOs;



import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BookingsList {
    private Integer hotelId;
    private Integer bookingId;
    private int adults;
    private int children;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String hotelName;
    private int hotelStars;
    private String hotelCity;
    private int nbrSingleRooms;
    private int nbrDoubleRooms;
    private int nbrDeluxeRooms;
    
}
