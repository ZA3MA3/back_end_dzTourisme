package com.example.springJwt.UserProfile.repositries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.RestaurantReservation.model.RestaurantReview;

import java.util.List;
import java.util.Optional;

    
@Repository
public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Integer> {
    List<RestaurantReview> findAllByUser(User user);
    Page<RestaurantReview>findAllByRestaurantId(Pageable page,Integer id);


}  