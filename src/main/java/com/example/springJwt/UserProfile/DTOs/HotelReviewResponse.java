package com.example.springJwt.UserProfile.DTOs;


import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class HotelReviewResponse {
    private List<ReviewDTO> reviews;
    private int totalPages;
    private int currentPage;
    private long totalRestaurants;}
