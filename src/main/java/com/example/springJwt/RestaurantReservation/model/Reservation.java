package com.example.springJwt.RestaurantReservation.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.springJwt.Authentication.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation")
@Entity
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    
    @Column(name = "clients")
    private int clients;

    
    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    
    @Column(name = "time")
    private LocalTime time;


    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "restaurant_time_table_id")
    private RestaurantTimeTable restaurantTimeTable;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    
}
