package com.example.springJwt.CarRenal.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RentalsList {
    private Integer rentalId;
    private String carBrand;
    private String carModel;
    private String carBodyType;
    private String engineType;
    private String protection;
    private Integer protectionId;
    private LocalDate pickUp;
    private LocalTime pickUpTime;
    private LocalDate dropOff;
    private LocalTime dropOffTime;
    private int additionalDriver;
    private int navigationalSystem;
    private int babySeat;
    private int childSeat;
    private int childBoosterSeat;
    private Integer carId;
    private String locationName;   
    private Integer userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;



}
