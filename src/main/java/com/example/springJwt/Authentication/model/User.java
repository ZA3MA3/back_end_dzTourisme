package com.example.springJwt.Authentication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.springJwt.CarRenal.model.Rental;
import com.example.springJwt.HotelBooking.model.Booking;
import com.example.springJwt.HotelBooking.model.HotelReview;
import com.example.springJwt.RestaurantReservation.model.Reservation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    
    
    @Column(name = "username")
    private String username;
    
    @Column(name="email")
    private String email;


    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;
    
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone_number")
    private String phoneNumber;


    @Enumerated(value = EnumType.STRING)
    private Role role;




    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Token> tokens;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Booking> bookings;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<HotelReview> reviews;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
        private List<Rental> rentals;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


}/* 
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Booking> bookings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
   
}*/