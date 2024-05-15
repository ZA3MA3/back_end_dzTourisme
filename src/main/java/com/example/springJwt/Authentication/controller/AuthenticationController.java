package com.example.springJwt.Authentication.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springJwt.Authentication.DTO.AuthRequestDTO;
import com.example.springJwt.Authentication.DTO.LocationSuggestion;
import com.example.springJwt.Authentication.model.AuthenticationResponse;
import com.example.springJwt.Authentication.service.AuthenticationService;
import com.example.springJwt.CarRenal.DTOs.CarAvailibilityReq;
import com.example.springJwt.CarRenal.model.Car;
import com.example.springJwt.CarRenal.model.PickUpLocation;
import com.example.springJwt.CarRenal.repositories.PickUpLocationRepository;
import com.example.springJwt.CarRenal.service.RentalService;
import com.example.springJwt.HotelBooking.DTOs.EmailRequest;
import com.example.springJwt.HotelBooking.DTOs.SearchRequest;
import com.example.springJwt.HotelBooking.Service.BookingService;
import com.example.springJwt.HotelBooking.model.City;
import com.example.springJwt.HotelBooking.model.Hotel;
import com.example.springJwt.HotelBooking.model.HotelImage;
import com.example.springJwt.HotelBooking.model.Room;
import com.example.springJwt.HotelBooking.repositories.CityRepository;
import com.example.springJwt.HotelBooking.repositories.HotelRepository;
import com.example.springJwt.HotelBooking.repositories.RoomRepository;
import com.example.springJwt.RestaurantReservation.DTOs.TimeTableRequest;
import com.example.springJwt.RestaurantReservation.model.Restaurant;
import com.example.springJwt.RestaurantReservation.model.RestaurantImage;
import com.example.springJwt.RestaurantReservation.repositories.RestaurantRepository;
import com.example.springJwt.RestaurantReservation.service.ReservationService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {


    @Autowired
    private  AuthenticationService authService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    BookingService bookingService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    RentalService rentalService;

    @Autowired
    private PickUpLocationRepository pickUpLocationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;








    @GetMapping("/{hotelId}/hotelImages")
    public ResponseEntity<HotelImage> getHotelImageById(@PathVariable Integer hotelId) {
        HotelImage hotelImage = bookingService.getHotelImageByHotelId(hotelId);
        if (hotelImage != null) {
            return ResponseEntity.ok().body(hotelImage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{restaurantId}/restaurantImages")
    public ResponseEntity<RestaurantImage> getRestaurantImageById(@PathVariable Integer restaurantId) {
        RestaurantImage restaurantImage = reservationService.getRestaurantImageByHotelId(restaurantId);
        if (restaurantImage != null) {
            return ResponseEntity.ok().body(restaurantImage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{pickUpLocation}/map")
    public ResponseEntity<String> getPickUpLocationMap(@PathVariable String pickUpLocation) {
        PickUpLocation pickUp=pickUpLocationRepository.findByName(pickUpLocation);

        if (pickUp != null) {
            return ResponseEntity.ok().body(pickUp.getMapIframe());
        } else {
            return ResponseEntity.ok().body("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d14469017.676004378!2d-8.987459755321106!3d27.702616244642265!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd7e8a6a28037bd1%3A0x7140bee3abd7f8a2!2zQWxnw6lyaWU!5e0!3m2!1sfr!2sdz!4v1714932207668!5m2!1sfr!2sdz");
        }
    }


    

    @GetMapping("/search/{locationName}")
    public ResponseEntity<List<Hotel>> searchHotels(@PathVariable String locationName) {
        if (locationName == null) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    
        List<Hotel> hotels = bookingService.searchHotels(locationName);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/restaurantSearch/{locationName}")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@PathVariable String locationName) {
        if (locationName == null) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    
        List<Restaurant> restaurants =reservationService.searchRestaurants(locationName);
        return ResponseEntity.ok(restaurants);
    }

   @PostMapping("/carSearch/{locationName}")
    public List<Car> getCarsByCityAndDates(
            @PathVariable String locationName,
            @RequestBody CarAvailibilityReq  request) {
        return rentalService.findAvailableCars(locationName, request.getPickUp(), request.getDropOff());
    }


    @GetMapping("/pick-up-locations/suggestions")
    public List<PickUpLocation> getPickUpLocationSuggestions(@RequestParam("query") String query) {
        // Use the repository to find pick-up locations containing the provided query
        return pickUpLocationRepository.findByNameContaining(query);
    }

    @GetMapping("/restaurantLocations/suggestions")
    public ResponseEntity<List<LocationSuggestion>> getRestaurantCitySuggestions(@RequestParam("query") String query) {
        // Use the repository to find pick-up locations containing the provided query
        List<City> cities = cityRepository.findByCityNameContainingIgnoreCase(query);
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCase(query);

        List<Restaurant> restaurantsByCityName = restaurantRepository.findByCity_CityNameContainingIgnoreCase(query);
    
        List<LocationSuggestion> suggestions = new ArrayList<>();
    
        // Add cities to suggestions
        for (City city : cities) {
            suggestions.add(new LocationSuggestion(city.getId(), city.getCityName(), "city"));
        }
    
        // Add hotels to suggestions
        for (Restaurant restaurant : restaurants) {
            suggestions.add(new LocationSuggestion(restaurant.getId(), restaurant.getName(), "restaurant"));
        }

        for (Restaurant restaurant : restaurantsByCityName) {
            suggestions.add(new LocationSuggestion(restaurant.getId(), restaurant.getName(), "restaurant"));
        }
    
    
        return ResponseEntity.ok().body(suggestions);
    }
    
    @GetMapping("/hotelLocations/suggestions")
    public ResponseEntity<List<LocationSuggestion>> getHotelCitySuggestions(@RequestParam("query") String query) {
       
        List<City> cities = cityRepository.findByCityNameContainingIgnoreCase(query);
        List<Hotel> hotels = hotelRepository.findByNameContainingIgnoreCase(query);
        
       
        List<Hotel> hotelsByCityName = hotelRepository.findByCity_CityNameContainingIgnoreCase(query);
    
        List<LocationSuggestion> suggestions = new ArrayList<>();
    
        
        for (City city : cities) {
            suggestions.add(new LocationSuggestion(city.getId(), city.getCityName(), "city"));
        }
    
        
        for (Hotel hotel : hotels) {
            suggestions.add(new LocationSuggestion(hotel.getId(), hotel.getName(), "hotel"));
        }
        
    
        for (Hotel hotel : hotelsByCityName) {
            suggestions.add(new LocationSuggestion(hotel.getId(), hotel.getName(), "hotel"));
        }
    
        return ResponseEntity.ok().body(suggestions);
    }
    

   
    

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthRequestDTO request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthRequestDTO request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }


    @PostMapping("/getEmail")
    public ResponseEntity<String> getEmail(@RequestBody EmailRequest EmailRequest){
     
        return ResponseEntity.ok(bookingService.findUserByToken(EmailRequest.getToken()));

    }

    @PostMapping("/getTimeTableId")
    public ResponseEntity<Integer> getTimeTableId(@RequestBody TimeTableRequest timeTableRequest){
     
        return ResponseEntity.ok(reservationService.findTimeTableByTime(timeTableRequest.getTime()));

    }
    
  
  

}
