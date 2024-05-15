package com.example.springJwt.UserProfile.DTOs;

import java.time.LocalDate;

import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.HotelBooking.model.HotelReview;
import com.example.springJwt.RestaurantReservation.model.RestaurantReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ReviewDTO {
    
    private Integer restaurantId;

    private Integer hotelId;

    private Integer userId;

    private User user;

    private Integer id;

    private LocalDate date;

    private String comment;

    private int rating;

    public ReviewDTO(HotelReview hotelReview) {
        this.id = hotelReview.getId();
        this.hotelId = hotelReview.getHotel().getId();
        this.userId = hotelReview.getUser().getId();
        this.user=hotelReview.getUser();
        this.date = hotelReview.getDate();
        this.comment = hotelReview.getComment();
        this.rating = hotelReview.getRating();
    }


    public ReviewDTO(RestaurantReview restaurantReview) {
        this.id = restaurantReview.getId();
        this.restaurantId = restaurantReview.getRestaurant().getId();
        this.userId = restaurantReview.getUser().getId();
        this.user = restaurantReview.getUser();
        this.date = restaurantReview.getDate();
        this.comment = restaurantReview.getComment();
        this.rating = restaurantReview.getRating();
    }
}
