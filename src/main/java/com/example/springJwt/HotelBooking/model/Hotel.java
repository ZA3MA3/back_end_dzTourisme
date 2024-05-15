package com.example.springJwt.HotelBooking.model;

import java.util.List;

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
import lombok.Getter;
import lombok.Setter;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "hotel")
@Entity
public class Hotel {
    
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

    // @Column(name = "nbr_single_room")
    // private int nbrSingleRoom;

    // @Column(name = "nbr_double_room")
    // private int nbrDoubleRoom;
    
    // @Column(name = "nbr_deluxe_room")
    // private int nbrDeluxeRoom;

    @OneToMany(mappedBy = "hotel")
    @JsonManagedReference
    private List<Room> rooms;

    


    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonManagedReference
    private City city;

    @OneToMany(mappedBy = "hotel")
    @JsonManagedReference
    private List<HotelReview> reviews;

    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_image_id")
    @JsonBackReference
    private HotelImage hotelImage;


}
 
/*  
 @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "hotel")
@Entity
public class Hotel {

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

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Room> rooms;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;
}*/
