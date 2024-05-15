package com.example.springJwt.RestaurantReservation.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.example.springJwt.RestaurantReservation.DTOs.ReservationEdit;
import com.example.springJwt.RestaurantReservation.DTOs.ReservationRequest;
import com.example.springJwt.RestaurantReservation.DTOs.ReservationsList;
import com.example.springJwt.RestaurantReservation.DTOs.TimeRequest;
import com.example.springJwt.RestaurantReservation.DTOs.TokenDTO;
import com.example.springJwt.RestaurantReservation.model.Reservation;
import com.example.springJwt.RestaurantReservation.model.RestaurantTimeTable;
import com.example.springJwt.RestaurantReservation.repositories.ReservationRepository;
import com.example.springJwt.RestaurantReservation.repositories.RestaurantTimeTableRepository;
import com.example.springJwt.RestaurantReservation.service.ReservationService;

import jakarta.transaction.Transactional;

@RestController
public class ReservationController {

    @Autowired
    RestaurantTimeTableRepository restaurantTimeTableRepository;

    @Autowired
    ReservationService reservationService;


    @Autowired
    ReservationService restaurantService;

    @Autowired
    ReservationRepository reservationRepository;

    @Transactional
    @PostMapping("/reserveTable")
    public ResponseEntity<String> reserveTable(@RequestBody ReservationRequest reservationRequest){
        try {
            reservationService.reserveTable(reservationRequest);
            return new ResponseEntity<>("Table reserved successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error reserving table: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/availableHours")
    public ResponseEntity<List<LocalTime>> getAvailableHours(
            @RequestBody TimeRequest request) {
        List<LocalTime> availableHours = restaurantService.getAvailableHours(
                request.getRestaurantId(),
                request.getHour(),request.getReservationDate());
        return ResponseEntity.ok(availableHours);
    }


    @PostMapping("/reservationsList")
    public ResponseEntity<List<ReservationsList>> reservationsList(@RequestBody TokenDTO tokenDTO) {
        String token=tokenDTO.getToken();
        List<ReservationsList> reservations = reservationService.reservationsList(token);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/deleteReservation/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Integer reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            LocalDateTime reservationDateTime = LocalDateTime.of(reservation.getReservationDate(), reservation.getTime());
            LocalDateTime currentDateTimeMinusOneDay = LocalDateTime.now().minusDays(1);
    
            // Check if the current date and time are before the reservation date and time
            if (currentDateTimeMinusOneDay.isBefore(reservationDateTime)) {
                try {
                    reservationRepository.deleteById(reservationId);
                    return new ResponseEntity<>("Reservation deleted successfully!", HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>("Error deleting reservation: " + e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Reservation cannot be deleted because the reservation date and time are less than 1 day away", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/editReservation")
public ResponseEntity<String> editReservation(@RequestBody ReservationEdit request) {
    Integer reservationId = request.getReservationId();
    Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
    if (optionalReservation.isPresent()) {
        Reservation reservation = optionalReservation.get();
        LocalDate reservationDate = reservation.getReservationDate();
        LocalTime reservationTime = reservation.getTime();
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);
        LocalDateTime currentDateTimeMinusOneDay = LocalDateTime.now().minusDays(1);

        // Check if the current date and time (minus one day) are before the reservation date and time
        if (currentDateTimeMinusOneDay.isBefore(reservationDateTime)) {
            try {
                // Proceed with editing the reservation
                reservationService.editReservation(request);
                return new ResponseEntity<>("Reservation updated successfully!", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating reservation: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Reservation cannot be edited. The deadline for editing has passed.", HttpStatus.BAD_REQUEST);
        }
    } else {
        return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
    }
}

    
}
