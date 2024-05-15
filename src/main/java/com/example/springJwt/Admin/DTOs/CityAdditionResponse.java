package com.example.springJwt.Admin.DTOs;
import java.util.List;

import com.example.springJwt.HotelBooking.model.City;

import java.math.BigDecimal;
import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CityAdditionResponse {
    private List<City> restaurantAdditions;
    private int totalPages;
    private int currentPage;
    private long totalCities;
}
