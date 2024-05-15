package com.example.springJwt.Admin.DTOs;

import java.util.List;

import java.math.BigDecimal;
import java.time.Year;
import com.example.springJwt.Authentication.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserAdditionResponse {
    private List<User> restaurantAdditions;
    private int totalPages;
    private int currentPage;
    private long totalUsers;
}
