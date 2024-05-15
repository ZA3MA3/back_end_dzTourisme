package com.example.springJwt.Admin.DTOs;


import java.math.BigDecimal;
import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CarAddition {
    
    private String brand;
    
    private String model;

    private BigDecimal price;

    private Year   year;

    private String bodyType;

    private String engineType;

    private boolean isAutomatic;

    private boolean hasAC;

    private int     seatingCapacity;

    private int     luggageCapacity;



}
