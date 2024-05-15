package com.example.springJwt.Admin.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.springJwt.Admin.DTOs.CarAddition;
import com.example.springJwt.Admin.DTOs.CarsAdditionResponse;
import com.example.springJwt.Admin.DTOs.CityAdditionResponse;
import com.example.springJwt.Admin.DTOs.RestaurantAddition;
import com.example.springJwt.Admin.DTOs.RestaurantAddtionResponse;
import com.example.springJwt.Admin.DTOs.UserAdditionResponse;
import com.example.springJwt.Admin.service.AdminService;
import com.example.springJwt.Authentication.DTO.AuthRequestDTO;
import com.example.springJwt.Authentication.model.AuthenticationResponse;
import com.example.springJwt.Authentication.model.Token;
import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.Authentication.repository.TokenRepository;
import com.example.springJwt.Authentication.repository.UserRepository;
import com.example.springJwt.Authentication.service.AuthenticationService;
import com.example.springJwt.CarRenal.model.Car;
import com.example.springJwt.CarRenal.model.Rental;
import com.example.springJwt.CarRenal.repositories.CarRepository;
import com.example.springJwt.CarRenal.repositories.RentalRepository;
import com.example.springJwt.HotelBooking.model.Booking;
import com.example.springJwt.HotelBooking.model.City;
import com.example.springJwt.HotelBooking.model.Hotel;
import com.example.springJwt.HotelBooking.repositories.BookingRepository;
import com.example.springJwt.HotelBooking.repositories.HotelRepository;
import com.example.springJwt.RestaurantReservation.model.Reservation;
import com.example.springJwt.RestaurantReservation.model.Restaurant;
import com.example.springJwt.RestaurantReservation.model.RestaurantTimeTable;
import com.example.springJwt.RestaurantReservation.repositories.ReservationRepository;
import com.example.springJwt.RestaurantReservation.repositories.RestaurantRepository;
import com.example.springJwt.RestaurantReservation.repositories.RestaurantTimeTableRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    ReservationRepository reservationRepository;
  
    @Autowired
    RestaurantTimeTableRepository restaurantTimeTableRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/isAdmin")
    public boolean isAdmin(@RequestParam("token") String token){
      return  adminService.isAdmin(token);
    } 

    @GetMapping("/usersList")
    public ResponseEntity<UserAdditionResponse> usersList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<User> usersPage = adminService.getAllUsers(pageable);
    
        int totalPages = usersPage.getTotalPages();
        int currentPage = usersPage.getNumber();
        long totalUsers=usersPage.getTotalElements();
    
        List<User> users = usersPage.getContent();
    
        UserAdditionResponse usersResponse = new UserAdditionResponse(users, totalPages, currentPage,totalUsers);
        return ResponseEntity.ok(usersResponse);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
    
                List<Token> userTokens = tokenRepository.findByUser(user);
                tokenRepository.deleteAll(userTokens);
    
                List<Booking> userBookings = bookingRepository.findByUser(user);
                bookingRepository.deleteAll(userBookings);
    
                List<Rental> userRentals = rentalRepository.findByUser(user);
                rentalRepository.deleteAll(userRentals);
    
                List<Reservation> userReservations = reservationRepository.findByUser(user);
                reservationRepository.deleteAll(userReservations);
    
                userRepository.deleteById(userId);
    
                return ResponseEntity.ok("User and associated data deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }


    // @DeleteMapping("/deleteRestaurant/{restaurantId}")
    // public ResponseEntity<String> deleteRestaurant(@PathVariable("restaurantId") Integer restaurantId) {
    //     try {
    //         Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
    //         if (optionalRestaurant.isPresent()) {
    //             Restaurant restaurant = optionalRestaurant.get();
                
    //             List<RestaurantTimeTable> restaurantTimeTables=restaurantTimeTableRepository.findByRestaurant(restaurant);
                
    //             restaurantTimeTableRepository.deleteAll(restaurantTimeTables);

    //             restaurantRepository.delete(restaurant);
    
    //             return ResponseEntity.ok("restaurant and associated data deleted successfully");
    //         } else {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("restaurant not found with ID: " + restaurantId);
    //         }
    //     } catch (EmptyResultDataAccessException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("restaurant not found with ID: " + restaurantId);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
    //     }
    // }
    

  
    @PostMapping("/addUser")
    public ResponseEntity<AuthenticationResponse> addUser(
            @RequestBody AuthRequestDTO request
            ) {
        return ResponseEntity.ok(adminService.addUser(request));
    }


    
    @GetMapping("/citiesList")
    public ResponseEntity<CityAdditionResponse> citiesList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<City> citiesPage = adminService.getAllCities(pageable);
    
        int totalPages = citiesPage.getTotalPages();
        int currentPage = citiesPage.getNumber();
        long totalCities=citiesPage.getNumberOfElements();
    
        List<City> cities = citiesPage.getContent();
    
        CityAdditionResponse citiesResponse = new CityAdditionResponse(cities, totalPages, currentPage,totalCities);
        return ResponseEntity.ok(citiesResponse);
    }


    
    @GetMapping("/restaurantsList")
    public ResponseEntity<RestaurantAddtionResponse> restaurantsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Restaurant> restaurantsPage = adminService.getAllRestaurants(pageable);
        List<RestaurantAddition> restaurantAdditions = restaurantsPage.getContent().stream()
                .map(restaurant -> {
                    RestaurantAddition restaurantAddition = new RestaurantAddition();
                    restaurantAddition.setName(restaurant.getName());
                    restaurantAddition.setDescription(restaurant.getDescription());
                    restaurantAddition.setStars(restaurant.getStars());
                    restaurantAddition.setCityName(restaurant.getCity().getCityName());
                    return restaurantAddition;
                })
                .collect(Collectors.toList());

        int totalPages = restaurantsPage.getTotalPages();
        int currentPage = restaurantsPage.getNumber();
        long totalRestaurants=restaurantsPage.getTotalElements();

        RestaurantAddtionResponse responseData = new RestaurantAddtionResponse(restaurantAdditions, totalPages, currentPage,totalRestaurants);
        return ResponseEntity.ok(responseData);
    }


    
    @PostMapping("/addRestaurant")
    public ResponseEntity<String> addRestaurant(
            @RequestBody RestaurantAddition request
            ) {
        return ResponseEntity.ok(adminService.addRestaurant(request));
    }


  @GetMapping("/hotelsList")
    public ResponseEntity<RestaurantAddtionResponse> hotelsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Hotel> hotelsPage = adminService.getAllHotels(pageable);
        List<RestaurantAddition> restaurantAdditions = hotelsPage.getContent().stream()
                .map(hotel -> {
                    RestaurantAddition restaurantAddition = new RestaurantAddition();
                    restaurantAddition.setName(hotel.getName());
                    restaurantAddition.setDescription(hotel.getDescription());
                    restaurantAddition.setStars(hotel.getStars());
                    restaurantAddition.setCityName(hotel.getCity().getCityName());
                    return restaurantAddition;
                })
                .collect(Collectors.toList());

        int totalPages = hotelsPage.getTotalPages();
        int currentPage = hotelsPage.getNumber();
        long totalHotels=hotelsPage.getNumberOfElements();

        RestaurantAddtionResponse responseData = new RestaurantAddtionResponse(restaurantAdditions, totalPages, currentPage,totalHotels);
        return ResponseEntity.ok(responseData);
    }



    @PostMapping("/addHotel")
    public ResponseEntity<String> addHotel(
            @RequestBody RestaurantAddition request
            ) {
        return ResponseEntity.ok(adminService.addHotel(request));
    }

    @GetMapping("/carsList")
    public ResponseEntity<CarsAdditionResponse> carsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Car> carsPage = adminService.getAllCars(pageable);
    
        int totalPages = carsPage.getTotalPages();
        int currentPage = carsPage.getNumber();
        long totalCars = carsPage.getTotalElements(); 
    
        List<Car> cars = carsPage.getContent();
    
        CarsAdditionResponse carsResponse = new CarsAdditionResponse(cars, totalPages, currentPage,totalCars);
        return ResponseEntity.ok(carsResponse); 
    }

    @PostMapping("/addCar")
    public ResponseEntity<String> addCar(
            @RequestBody CarAddition request
            ) {
        return ResponseEntity.ok(adminService.addCar(request));
    }




}
