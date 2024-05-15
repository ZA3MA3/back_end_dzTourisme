package com.example.springJwt.UserProfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springJwt.HotelBooking.model.HotelReview;
import com.example.springJwt.RestaurantReservation.model.RestaurantReview;
import com.example.springJwt.UserProfile.repositries.HotelReviewRepository;
import com.example.springJwt.UserProfile.repositries.RestaurantReviewRepository;

@Service
public class ReviewsService {
    
    @Autowired
    HotelReviewRepository hotelReviewRepository;
    @Autowired
    RestaurantReviewRepository restaurantReviewRepository;

    public Page<HotelReview> getAllHotelReviews(Pageable pageable,Integer id) {
        return hotelReviewRepository.findAllByHotelId(pageable, id);
    }

    public Page<RestaurantReview> getAllRestaurantReviews(Pageable pageable,Integer id) {
        return restaurantReviewRepository.findAllByRestaurantId(pageable,id);
    }
}
