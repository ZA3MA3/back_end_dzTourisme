package com.example.springJwt.HotelBooking.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.springJwt.Authentication.model.Token;
import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.Authentication.repository.TokenRepository;
import com.example.springJwt.Authentication.repository.UserRepository;
import com.example.springJwt.HotelBooking.DTOs.BookingRequest;
import com.example.springJwt.HotelBooking.DTOs.BookingsList;
import com.example.springJwt.HotelBooking.model.Booking;
import com.example.springJwt.HotelBooking.model.City;
import com.example.springJwt.HotelBooking.model.Hotel;
import com.example.springJwt.HotelBooking.model.HotelImage;
import com.example.springJwt.HotelBooking.model.Room;
import com.example.springJwt.HotelBooking.model.RoomType;
import com.example.springJwt.HotelBooking.repositories.BookingRepository;
import com.example.springJwt.HotelBooking.repositories.CityRepository;
import com.example.springJwt.HotelBooking.repositories.HotelRepository;
import com.example.springJwt.HotelBooking.repositories.RoomRepository;

import io.jsonwebtoken.lang.Collections;
import jakarta.transaction.Transactional;

@Service
public class BookingService {
    @Autowired
    private  BookingRepository bookingRepository;
    // private final BookingRequest bookingRequest;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoomRepository roomRepository;

    @Autowired
    private  TokenRepository tokenRepository;
    @Autowired
    private  HotelRepository hotelRepository;
    @Autowired
    private  CityRepository cityRepository;
    

    
    public List<Hotel> searchHotels(String locationName) {
        try {
            // Check if there's a hotel with the given name
            List<Hotel> hotelsByName = hotelRepository.findByNameContainingIgnoreCase(locationName);
            if (!hotelsByName.isEmpty()) {
                return  hotelsByName;
            }
    
            // If no hotel found by name, search for a city
            City city = cityRepository.findByCityNameIgnoreCase(locationName);
            if (city != null) {
                // If city found, get hotels associated with that city
                return  city.getHotels();
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
    public void bookRoom(BookingRequest bookingRequest) {
        // Create a new Booking entity
        Booking booking = new Booking();
        booking.setAdults(bookingRequest.getAdults());
        booking.setChildren(bookingRequest.getChildren());
        booking.setCheckIn(bookingRequest.getCheckIn());
        booking.setCheckOut(bookingRequest.getCheckOut());

        // Calculate total number of guests
        booking.calculateTotalNumberOfGuests();

        // Retrieve user by their token
        User user = userRepository.findByTokens_Token(bookingRequest.getToken());
        if (user == null) {
            // Handle the case where the user is not found by the token
            throw new RuntimeException("User not found for the provided token");
        }

        // Update user details
        user.setFirstname(bookingRequest.getFirstName());
        user.setLastname(bookingRequest.getLastName());
        user.setPhoneNumber(bookingRequest.getPhoneNumber());

        // Save/update the user
        userRepository.save(user);

        // Set the user for the booking
        booking.setUser(user);

        // Retrieve rooms by their IDs
        List<Room> bookedRooms = roomRepository.findAllById(bookingRequest.getBookedRoomIds()); 

        // Set the rooms for the booking
        booking.setRooms(bookedRooms);

        // Save the booking
        bookingRepository.save(booking);

        for (Room room : bookedRooms) {
            // Check if the room is not already booked
            if (!room.isBooked()) {
                // Update isBooked to true
                room.setBooked(true);
                // Save the updated room
                roomRepository.save(room);
            }
        }
    }
    


    public Long getCurrentUser() {
        
        Long userId= tokenRepository.findUserIdByIsLoggedOutIsFalse();

        return userId;
    }

    
    public String findUserByToken(String token) {
        User user=userRepository.findByTokens_Token(token);
        
        return user.getEmail();
    }



/*  public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, Integer hotelId) {
        List<Room> availableRooms = roomRepository.findByHotelIdAndIsBookedFalse(hotelId);

        List<Booking> bookings = bookingRepository.findByCheckInBetweenOrCheckOutBetween(checkInDate, checkOutDate, checkInDate, checkOutDate);

        for (Booking booking : bookings) {
            for (Room room : booking.getRooms()) {
                if (room.isBooked()) {
                    // If the room is already booked, check if the booking is for the same hotel
                    if (room.getHotel().getId().equals(hotelId)) {
                        // Check if the booking dates don't overlap with the requested dates
                        if (!(checkInDate.isAfter(booking.getCheckOut()) || checkOutDate.isBefore(booking.getCheckIn()))) {
                            availableRooms.remove(room);
                        }
                    }
                }
            }
        }
        return availableRooms;
    }*/   


    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, Integer hotelId) {
        List<Room> availableRooms = new ArrayList<>();
    
        // Retrieve rooms that are not booked for the specified hotel
        List<Room> notBookedRooms = roomRepository.findByHotelIdAndIsBookedFalse(hotelId);
        availableRooms.addAll(notBookedRooms);
    
        // Retrieve rooms that are booked for the specified hotel
        List<Room> bookedRooms = roomRepository.findByHotelIdAndIsBookedTrue(hotelId);
    
        // Iterate through each booked room
        for (Room room : bookedRooms) {
            boolean isAvailable = true;
    
            // Check if the room has any overlapping bookings
            for (Booking booking : room.getBookings()) {
                // If the booking is within the specified dates, the room is not available
                if (!(checkInDate.isAfter(booking.getCheckOut()) || checkOutDate.isBefore(booking.getCheckIn()))) {
                    isAvailable = false;
                    break;
                }
            }
    
            // If the room is available for all specified dates, add it to the available rooms list
            if (isAvailable) {
                availableRooms.add(room);
            }
        }
    
        return availableRooms;
    }

    
    public List<BookingsList> bookingsList(String token) {
        User user = userRepository.findByTokens_Token(token);
        if (user == null) {
            // Handle the case where the user is not found by the token
            throw new RuntimeException("User not found for the provided token");
        }
    
        List<Booking> bookings = bookingRepository.findByUser(user);
        List<BookingsList> bookingsList = new ArrayList<>();
    
        if (!bookings.isEmpty()) {
            for (Booking booking : bookings) {
                BookingsList dto = new BookingsList();
                dto.setBookingId(booking.getId());
                dto.setAdults(booking.getAdults());
                dto.setChildren(booking.getChildren());
                dto.setCheckIn(booking.getCheckIn());
                dto.setCheckOut(booking.getCheckOut());
                Hotel hotel = booking.getRooms().get(0).getHotel();
                dto.setHotelId(hotel.getId());
                dto.setHotelName(hotel.getName());
                dto.setHotelStars(hotel.getStars());
                dto.setHotelCity(hotel.getCity().getCityName());
                int nbrSingleRooms = 0;
                int nbrDoubleRooms = 0;
                int nbrDeluxeRooms = 0;
                for (Room room : booking.getRooms()) {
                    RoomType roomType = room.getRoomType();
                    if (roomType.getId() == 1) {
                        nbrSingleRooms++;
                    } else if (roomType.getId() == 2) {
                        nbrDoubleRooms++;
                    } else if (roomType.getId() == 3) {
                        nbrDeluxeRooms++;
                    }
                }
                dto.setNbrSingleRooms(nbrSingleRooms);
                dto.setNbrDoubleRooms(nbrDoubleRooms);
                dto.setNbrDeluxeRooms(nbrDeluxeRooms);
                bookingsList.add(dto);
            }
        }
        
        return bookingsList;
    }
    

    public void editBooking(BookingRequest request) {
        Optional<Booking> optionalBooking = bookingRepository.findById(request.getBookingId());
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setAdults(request.getAdults());
            booking.setChildren(request.getChildren());
            booking.setCheckIn(request.getCheckIn());
            booking.setCheckOut(request.getCheckOut());
            
            // Calculate total number of guests
            booking.calculateTotalNumberOfGuests();
            
            User user = userRepository.findByTokens_Token(request.getToken());
            if (user == null) {
                // Handle the case where the user is not found by the token
                throw new RuntimeException("User not found for the provided token");
            }
            
            user.setFirstname(request.getFirstName());
            user.setLastname(request.getLastName());
            user.setPhoneNumber(request.getPhoneNumber());
    
            // Save/update the user
            userRepository.save(user);
    
            booking.setUser(user);
            List<Room> existingRooms = new ArrayList<>(booking.getRooms());
    
            booking.getRooms().clear();
    
            // Retrieve rooms by their IDs
            List<Room> bookedRooms = roomRepository.findAllById(request.getBookedRoomIds());
    
            // Set the rooms for the booking
            booking.setRooms(bookedRooms);
    
            bookingRepository.save(booking);

            for (Room room : existingRooms) {
                // Check if the room is no longer associated with any booking
                if (room.getBookings().isEmpty()) {
                    // Update isBooked to false
                    room.setBooked(false);
                    // Save the updated room
                    roomRepository.save(room);
                }
            }
    
    
            for (Room room : bookedRooms) {
                // Check if the room is not already booked
                if (!room.isBooked()) {
                    // Update isBooked to true
                    room.setBooked(true);
                    // Save the updated room
                    roomRepository.save(room);
                }
            }
        }else{
            throw new RuntimeException("Booking not found with ID: " + request.getBookingId());
        }
    }


    
    public HotelImage getHotelImageByHotelId(Integer hotelId) {
        // Find the hotel by its ID
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        // Retrieve the hotel image associated with the hotel
        return hotel.getHotelImage();
    }


}
