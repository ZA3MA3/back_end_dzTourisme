package com.example.springJwt.Admin.DTOs;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RestaurantAddtionResponse {
    
        private List<RestaurantAddition> restaurantAdditions;
        private int totalPages;
        private int currentPage;
        private long totalRestaurants;
}

