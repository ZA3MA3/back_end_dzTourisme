package com.example.springJwt.Admin.DTOs;

import java.util.List;

import com.example.springJwt.CarRenal.model.Car;

import java.math.BigDecimal;
import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CarsAdditionResponse {
    
     
    private List<Car> restaurantAdditions;
    private int totalPages;
    private int currentPage;
    private long totalCars;

}
