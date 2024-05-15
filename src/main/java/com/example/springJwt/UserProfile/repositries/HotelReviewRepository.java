package com.example.springJwt.UserProfile.repositries;
  import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.HotelBooking.model.HotelReview;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReview, Integer> {
    List<HotelReview> findAllByUser(User user);
    Page<HotelReview>findAllByHotelId(Pageable page,Integer id);


}
