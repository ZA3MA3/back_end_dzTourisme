package com.example.springJwt.CarRenal.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springJwt.CarRenal.DTOs.RentalsList;
import com.example.springJwt.CarRenal.DTOs.RentalRequest;
import com.example.springJwt.CarRenal.model.Rental;
import com.example.springJwt.CarRenal.repositories.RentalRepository;
import com.example.springJwt.CarRenal.service.RentalService;
import com.example.springJwt.RestaurantReservation.DTOs.TokenDTO;

import jakarta.transaction.Transactional;

@RestController
public class RentalController {
    
    @Autowired
    RentalService rentalService;

    @Autowired
    RentalRepository rentalRepository;
    
    @Transactional
    @PostMapping("/rentCar")
    public ResponseEntity<String> bookRoom(@RequestBody RentalRequest rentalRequest) {
        try {
            rentalService.rentCar(rentalRequest);
            return new ResponseEntity<>("Car rented successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error renting car: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/rentalsList")
        public ResponseEntity<List<RentalsList>> rentalsList(@RequestBody TokenDTO tokenDTO) {
            String token=tokenDTO.getToken();
            List<RentalsList> rentals = rentalService.rentalsList(token);
            return ResponseEntity.ok(rentals);
    }


    @DeleteMapping("/deleteRental/{rentalId}")
    public ResponseEntity<String> deleteRental(@PathVariable Integer rentalId) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            LocalDateTime rentalDateTime = LocalDateTime.of(rental.getPickUp(), rental.getPickUpTime());
            LocalDateTime currentDateTimeMinusOneDay = LocalDateTime.now().minusDays(1);
    
            // Check if the current date and time are before the reservation date and time
            if (currentDateTimeMinusOneDay.isBefore(rentalDateTime)) {
                try {
                    rentalRepository.deleteById(rentalId);
                    return new ResponseEntity<>("Rental deleted successfully!", HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>("Error deleting rental: " + e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Rental cannot be deleted because the pick-up date and time are less than 1 day away.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("rental not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editRental")
    public ResponseEntity<String> editRental(@RequestBody RentalsList request) {
       
            LocalDate today = LocalDate.now();
            LocalDate oneDayBeforeCheckIn = request.getPickUp().minus(1, ChronoUnit.DAYS);
        
            // Check if it's one day before the check-in date
           // Check if it's one day before the check-in date
            if (today.isBefore(oneDayBeforeCheckIn)) {
                try {
                    rentalService.editRental(request);
                    return ResponseEntity.ok("Rental edited successfully.");
                } catch (RuntimeException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error editing rental: " + e.getMessage());
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Rental can only be edited one day before check-in.");
            }
        
    }

    
}
