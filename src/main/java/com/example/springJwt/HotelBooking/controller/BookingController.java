package com.example.springJwt.HotelBooking.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springJwt.Authentication.DTO.AvailibilityReq;
import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.Authentication.repository.UserRepository;
import com.example.springJwt.HotelBooking.DTOs.BookingRequest;
import com.example.springJwt.HotelBooking.DTOs.BookingsList;
import com.example.springJwt.HotelBooking.DTOs.SearchRequest;
import com.example.springJwt.HotelBooking.Service.BookingService;
import com.example.springJwt.HotelBooking.model.Booking;
import com.example.springJwt.HotelBooking.model.Hotel;
import com.example.springJwt.HotelBooking.model.Room;
import com.example.springJwt.HotelBooking.repositories.BookingRepository;
import com.example.springJwt.HotelBooking.repositories.RoomRepository;
import com.example.springJwt.RestaurantReservation.DTOs.TokenDTO;

import jakarta.transaction.Transactional;



@RestController
public class BookingController {
    @Autowired
    private  BookingService bookingService;
    @Autowired
    private RoomRepository roomRepository;
   
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    @PostMapping("/bookRoom")
    public ResponseEntity<String> bookRoom(@RequestBody BookingRequest bookingRequest) {
        try {
            bookingService.bookRoom(bookingRequest);
            return new ResponseEntity<>("Room booked successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error booking room: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // @PostMapping("/available")
    // public ResponseEntity<List<Room>> available(@RequestBody AvailibilityReq request){

    //     List<Room> AvRooms=roomRepository.findRoomToBook(request.getCheckIn(), request.getCheckOut() ,request.getHotelId());

    //     return ResponseEntity.ok(AvRooms);

    // }

    @PostMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms(@RequestBody AvailibilityReq request) {
        List<Room> availableRooms = bookingService.getAvailableRooms(request.getCheckIn(), request.getCheckOut(), request.getHotelId());
        return ResponseEntity.ok(availableRooms);
    }


    @PostMapping("/bookingsList")
    public ResponseEntity<List<BookingsList>> bookingsList(@RequestBody TokenDTO tokenDTO) {
        String token=tokenDTO.getToken();
        List<BookingsList> bookings =bookingService.bookingsList(token);
        return ResponseEntity.ok(bookings);
    }

   

    @DeleteMapping("/deleteBooking/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Integer bookingId) {
    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

    LocalDate today = LocalDate.now();
    LocalDate oneDayBeforeCheckIn = booking.getCheckIn().minus(1, ChronoUnit.DAYS);

    // Check if it's one day before the check-in date
    if (today.isBefore(oneDayBeforeCheckIn)) {
        

        // Delete the tuples from the association table booking_room
        for (Room room : booking.getRooms()) {
            room.getBookings().remove(booking);
            if (room.getBookings().isEmpty()) {
                room.setBooked(false);
                
            }     
        }
            roomRepository.saveAll(booking.getRooms());
            // Delete the booking from the booking table
            bookingRepository.delete(booking);
            
            // room.getBookings().removeIf(bookingInRoom -> bookingInRoom.getRooms().contains(room));
        
            return ResponseEntity.ok("Booking deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Booking can only be deleted one day before check-in.");
        }
    }

    @PutMapping("/editBooking")
    public ResponseEntity<String> editBooking(@RequestBody BookingRequest request) {
       
            LocalDate today = LocalDate.now();
            LocalDate oneDayBeforeCheckIn = request.getCheckIn().minus(1, ChronoUnit.DAYS);
        
            // Check if it's one day before the check-in date
           // Check if it's one day before the check-in date
            if (today.isBefore(oneDayBeforeCheckIn)) {
                try {
                    bookingService.editBooking(request);
                    return ResponseEntity.ok("Booking edited successfully.");
                } catch (RuntimeException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error editing booking: " + e.getMessage());
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Booking can only be edited one day before check-in.");
            }
        
    
    }





  

    
   
    

}
