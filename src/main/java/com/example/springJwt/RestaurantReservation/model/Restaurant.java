package com.example.springJwt.RestaurantReservation.model;



import java.util.List;

import com.example.springJwt.HotelBooking.model.City;
import com.example.springJwt.HotelBooking.model.HotelReview;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurant")
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

       
    @Column(name = "name")
    private String name;

    
    @Column(name = "description")
    private String description;

    @Column(name = "stars")
    private int stars;

    @Column(name = "primary_image")
    private String primaryImage;

    @Column(name = "map_iframe")
    private String mapIframe;


    @OneToMany(mappedBy = "restaurant")
    @JsonBackReference
    private List<RestaurantTimeTable> restaurantTimeTables ;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private List<RestaurantReview> reviews;


    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_image_id")
    @JsonBackReference
    private RestaurantImage restaurantImage;

    


}
