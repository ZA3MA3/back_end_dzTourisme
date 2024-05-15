package com.example.springJwt.RestaurantReservation.model;


import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "time_table")
@Entity
public class TimeTable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "time")
    private LocalTime time;


    @OneToMany(mappedBy = "timeTable")
    @JsonBackReference
    private List<RestaurantTimeTable> restaurantTimeTables ;

   

}   
