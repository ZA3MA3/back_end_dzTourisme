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
public class RentalRequest {
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private LocalDate pickUpDate;
  private LocalTime pickUpTime;
  private LocalDate dropOffDate;
  private LocalTime dropOffTime;
  private int additionalDriver ;
  private int navigationalSystem;
  private int babySeat;
  private int childSeat;
  private int childBoosterSeat;
  private Integer carId;
  private int protection;
  private String token;
  private String query;
}
