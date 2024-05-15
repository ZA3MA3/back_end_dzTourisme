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
public class BookingRequest {

    private Integer bookingId;

    private Integer userId;
    
    private int adults;

    private int children;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private String firstName;

    private String lastName;
    
    private String phoneNumber;

    private String token;
    
    private List<Integer> bookedRoomIds;
    
}
