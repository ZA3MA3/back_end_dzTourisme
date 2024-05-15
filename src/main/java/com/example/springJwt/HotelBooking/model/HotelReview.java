package com.example.springJwt.HotelBooking.model;

import java.time.LocalDate;

import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.RestaurantReservation.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 @AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "hotel_review")
@Entity
public class HotelReview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="review_date")
    private LocalDate date;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

   

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

}

/*@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "review")
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private String rating;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
}
 */
