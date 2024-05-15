package com.example.springJwt.Admin.DTOs;


import java.time.LocalTime;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RestaurantAddition {

    private String name;
    private String description;
    private int stars;
    private String cityName;
    private int totalPages;
    private int currentPage;


}
