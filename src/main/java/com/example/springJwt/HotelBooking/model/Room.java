package com.example.springJwt.HotelBooking.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "room")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "room_price")
    private BigDecimal price;

    @Column(name = "is_booked")
    private boolean isBooked;


    @ManyToMany(mappedBy = "rooms")
    @JsonBackReference
    private List<Booking> bookings;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;



}

/* 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "room")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "room_price")
    private BigDecimal price;

    @Column(name = "is_booked")
    private boolean isBooked;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToMany(mappedBy = "rooms")
    @JsonBackReference
    private List<Booking> bookings;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}*/
