package com.example.springJwt.HotelBooking.model;

import java.util.List;

import com.example.springJwt.CarRenal.model.Car;
import com.example.springJwt.CarRenal.model.PickUpLocation;
import com.example.springJwt.RestaurantReservation.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "city")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "city_name")
    private String cityName;


    @OneToMany(mappedBy = "city")
    @JsonBackReference
    private List<Hotel> hotels;

   
    @OneToMany(mappedBy = "city")
    @JsonBackReference
    private List<Restaurant> restaurants;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<PickUpLocation> pickUpLocations;
    
}/* 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "city")
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "city_name")
    private String cityName;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Hotel> hotels;
}*/