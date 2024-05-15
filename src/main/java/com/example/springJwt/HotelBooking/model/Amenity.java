package com.example.springJwt.HotelBooking.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "amenity")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Amenity {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amenity_name")
    private String name;

    @ManyToMany(mappedBy = "amenities")
    private List<RoomType> roomTypes;


}
//  TV,
//     WIFI,
//     AIR_CONDITIONING,
//     DEDICATED_WORKSPACE,
//     KITCHEN,
//     WASHER,
//     DRYER,
//     COFFEE_MAKER
/*
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "amenity")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amenity_name")
    private String name;

    @ManyToMany(mappedBy = "amenities")
    @JsonBackReference
    private List<RoomType> roomTypes;
}*/