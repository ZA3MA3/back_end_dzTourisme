package com.example.springJwt.RestaurantReservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.Authentication.repository.UserRepository;
import com.example.springJwt.HotelBooking.model.City;
import com.example.springJwt.HotelBooking.repositories.CityRepository;
import com.example.springJwt.RestaurantReservation.DTOs.ReservationEdit;
import com.example.springJwt.RestaurantReservation.DTOs.ReservationRequest;
import com.example.springJwt.RestaurantReservation.DTOs.ReservationsList;
import com.example.springJwt.RestaurantReservation.model.Reservation;
import com.example.springJwt.RestaurantReservation.model.Restaurant;
import com.example.springJwt.RestaurantReservation.model.RestaurantImage;
import com.example.springJwt.RestaurantReservation.model.RestaurantTimeTable;
import com.example.springJwt.RestaurantReservation.model.TimeTable;
import com.example.springJwt.RestaurantReservation.repositories.ReservationRepository;
import com.example.springJwt.RestaurantReservation.repositories.RestaurantRepository;
import com.example.springJwt.RestaurantReservation.repositories.RestaurantTimeTableRepository;
import com.example.springJwt.RestaurantReservation.repositories.TimeTableRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReservationService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantTimeTableRepository restaurantTimeTableRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    TimeTableRepository timeTableRepository;

    
    
   /*public List<LocalTime> getAvailableHours(Integer restaurantId, LocalTime hour) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + restaurantId));

        List<LocalTime> availableHours = new ArrayList<>();

        for (RestaurantTimeTable restaurantTimeTable : restaurant.getRestaurantTimeTables()) {
            LocalTime time = restaurantTimeTable.getTimeTable().getTime();
            if (time.isAfter(hour.minusMinutes(90)) && time.isBefore(hour.plusMinutes(90))) {
                availableHours.add(time);
            }
        }

        LocalTime startHour = LocalTime.parse("00:00");
        LocalTime endHour = LocalTime.parse("00:00");


        if (!availableHours.isEmpty()) {
            // Add start hour if it's within the available range
            if (startHour.isAfter(hour.minusMinutes(90)) && startHour.isBefore(hour.plusMinutes(90))) {
                availableHours.add(startHour);
            }
            // Add end hour if it's within the available range
            if (endHour.isAfter(hour.minusMinutes(90)) && endHour.isBefore(hour.plusMinutes(90))) {
                availableHours.add(endHour);
            }
        }

        Collections.sort(availableHours);
        return availableHours;
    }*/ 
    public List<LocalTime> getAvailableHours(Integer restaurantId, LocalTime hour, LocalDate reservationDate) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + restaurantId));
    
        List<LocalTime> availableHours = new ArrayList<>();
    
        for (RestaurantTimeTable restaurantTimeTable : restaurant.getRestaurantTimeTables()) {
            // Check if the reservation date matches the given date
            if (restaurantTimeTable.getReservations().stream()
                    .anyMatch(reservation -> reservation.getReservationDate().equals(reservationDate))) {
                continue; // Skip this time slot if it's already reserved for the given date
            }
            LocalTime time = restaurantTimeTable.getTimeTable().getTime();
            if (time.isAfter(hour.minusMinutes(90)) && time.isBefore(hour.plusMinutes(90))) {
                availableHours.add(time);
            }
        }
    
        LocalTime startHour = LocalTime.parse("00:00");
        LocalTime endHour = LocalTime.parse("00:00");
    
        if (!availableHours.isEmpty()) {
            // Add start hour if it's within the available range
            if (startHour.isAfter(hour.minusMinutes(90)) && startHour.isBefore(hour.plusMinutes(90))) {
                availableHours.add(startHour);
            }
            // Add end hour if it's within the available range
            if (endHour.isAfter(hour.minusMinutes(90)) && endHour.isBefore(hour.plusMinutes(90))) {
                availableHours.add(endHour);
            }
        }
    
        Collections.sort(availableHours);
        return availableHours;
    }
    
    public List<Restaurant> searchRestaurants(String locationName) {
            try {
                // Check if there's a hotel with the given name
                List<Restaurant> restaurantsByName = restaurantRepository.findByNameContainingIgnoreCase(locationName);
                if (!restaurantsByName.isEmpty()) {
                    return restaurantsByName;
                }
        
                // If no hotel found by name, search for a city
                City city = cityRepository.findByCityNameIgnoreCase(locationName);
                if (city != null) {
                    // If city found, get hotels associated with that city
                    return  city.getRestaurants();
                }
        
            } catch (Exception e) {
                // Log the exception or handle it appropriately
                e.printStackTrace();
                // You might want to throw a custom exception or return an error indicator here
                return  Collections.emptyList();
            }
        
            // No hotels found by name or city, return an empty list or null as per your requirement
            return  Collections.emptyList(); // or return null;
        
    
    }

    @Transactional
    public void reserveTable(ReservationRequest reservationRequest )  {
      
      
        User user = userRepository.findByTokens_Token(reservationRequest.getToken());
        if (user == null) {
            // Handle the case where the user is not found by the token
            throw new RuntimeException("User not found for the provided token");
        }

        // Update user details
        user.setFirstname(reservationRequest.getFirstName());
        user.setLastname(reservationRequest.getLastName());
        user.setPhoneNumber(reservationRequest.getPhoneNumber());

        // Save/update the user
        userRepository.save(user);

        RestaurantTimeTable restaurantTimeTable = restaurantTimeTableRepository.findByRestaurantIdAndTimeTableId(reservationRequest.getRestaurantId(), reservationRequest.getTimeTableId());
        if (restaurantTimeTable == null) {
            
            throw new RuntimeException("Restaurant time table not found");
        
        }



        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationRequest.getReservationDate());
        reservation.setTime(reservationRequest.getTime());
        reservation.setClients(reservationRequest.getClients());
        reservation.setRestaurantTimeTable(restaurantTimeTable);
        reservation.setUser(user);
        
        

        reservationRepository.save(reservation);


    }

   

    public Integer findTimeTableByTime(String time) {
        LocalTime localTime = LocalTime.parse(time);

        TimeTable timeTable=timeTableRepository.findByTime(localTime);
       
        return timeTable.getId();
    }

    public List<ReservationsList> reservationsList(String token) {
       
        User user = userRepository.findByTokens_Token(token);
        if (user == null) {
            // Handle the case where the user is not found by the token
            throw new RuntimeException("User not found for the provided token");
        }


        // Get reservations for the user
        List<Reservation> reservations = reservationRepository.findByUser(user);

        // Prepare DTOs for response
        List<ReservationsList> reservationsList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationsList dto = new ReservationsList();
            dto.setId(reservation.getId());
            dto.setClients(reservation.getClients());
            dto.setReservationDate(reservation.getReservationDate());
            dto.setTime(reservation.getTime());
            dto.setUserId(user.getId());
        

            // Get restaurant name
            RestaurantTimeTable restaurantTimeTable = reservation.getRestaurantTimeTable();   
            dto.setRestaurantTimeTableId(restaurantTimeTable.getId());
            if (restaurantTimeTable != null) {
                Restaurant restaurant = restaurantTimeTable.getRestaurant();
                if (restaurant != null) {
                    dto.setRestaurantId(restaurant.getId());
                    dto.setRestaurantName(restaurant.getName());
                    dto.setRestaurantStars(restaurant.getStars());
                    City city=cityRepository.findByRestaurants_Name(restaurant.getName());
                    dto.setRestaurantCity(city.getCityName());

                }
            }
            
            reservationsList.add(dto);
        }
        return reservationsList;
    }

    public void editReservation(ReservationEdit request) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(request.getReservationId());
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setClients(request.getClients());
            reservation.setReservationDate(request.getReservationDate());
            reservation.setTime(request.getTime());
          
            
            RestaurantTimeTable restaurantTimeTable = restaurantTimeTableRepository.findByRestaurantIdAndTimeTableId(request.getRestaurantId(), request.getTimeTableId());
            if (restaurantTimeTable == null) {
               
                throw new RuntimeException("Restaurant time table not found");
            
            }
            
            reservation.setRestaurantTimeTable(restaurantTimeTable);

            
            Optional<User> OptionalUser=userRepository.findById(request.getUserId());
            if (OptionalUser.isPresent()) {
            
            User user = OptionalUser.get();
            user.setFirstname(request.getFirstName());
            user.setLastname(request.getLastName());
            user.setPhoneNumber(request.getPhoneNumber());

            reservation.setUser(user);
            }

            reservationRepository.save(reservation);
        } else {
            throw new IllegalArgumentException("Reservation with ID " + request.getReservationId() + " not found");
        }
    }

    public RestaurantImage getRestaurantImageByHotelId(Integer restaurantId) {
        // Find the hotel by its ID
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + restaurantId));

        // Retrieve the hotel image associated with the hotel
        return restaurant.getRestaurantImage();
    }




}
