package com.example.springJwt.CarRenal.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.springJwt.Authentication.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "rental")
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "pick_up")
    private LocalDate pickUp; 

    @Column(name = "pick_up_time")
    private LocalTime pickUpTime; 

    @Column(name = "drop_off")
    private LocalDate dropOff;

    @Column(name = "drop_off_time")
    private LocalTime dropOffTime;

    @Column(name = "additional_driver")
    private int additionalDriver;

    @Column(name = "navigational_system")
    private int navigationalSystem;
    
    @Column(name = "baby_seat")
    private int babySeat;

    
    @Column(name = "child_seat")
    private int childSeat;

    
    @Column(name = "child_booster_seat")
    private int childBoosterSeat;

    @Column(name = "location_name")
    private String locationName;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "car_id")
    private Car car;
    

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "protection_id")
    private Protection protection;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

}
