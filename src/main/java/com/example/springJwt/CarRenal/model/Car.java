package com.example.springJwt.CarRenal.model;


import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import com.example.springJwt.HotelBooking.model.City;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "car")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Car {
      
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "brand")
    private String brand;

    
    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "year")
    private Year year;

    @Column(name = "body_type")
    private String bodyType;

    @Column(name = "is_automatic")
    private boolean isAutomatic;

    @Column(name = "has_ac")
    private boolean hasAC;

    @Column(name = "seating_capacity")
    private int seatingCapacity;

    @Column(name = "luggage_capacity")
    private int luggageCapacity;

    @Column(name = "primary_image")
    private String primaryImage;

 

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Rental> rentals;
    

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "car_location",
        joinColumns = { @JoinColumn(name = "car_id") },
        inverseJoinColumns = { @JoinColumn(name = "location_id") }
    )
    private List<PickUpLocation> locations;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "engine_type_id")
    private EngineType engineType;
   
}
