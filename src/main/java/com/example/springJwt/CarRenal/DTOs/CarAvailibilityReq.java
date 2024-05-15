package com.example.springJwt.CarRenal.DTOs;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CarAvailibilityReq {
    private LocalDate pickUp;
    private LocalDate dropOff;
}
