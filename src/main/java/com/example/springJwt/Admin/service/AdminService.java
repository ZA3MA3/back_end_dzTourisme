package com.example.springJwt.Admin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springJwt.Admin.DTOs.CarAddition;
import com.example.springJwt.Admin.DTOs.RestaurantAddition;
import com.example.springJwt.Authentication.DTO.AuthRequestDTO;
import com.example.springJwt.Authentication.model.AuthenticationResponse;
import com.example.springJwt.Authentication.model.Role;
import com.example.springJwt.Authentication.model.Token;
import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.Authentication.repository.TokenRepository;
import com.example.springJwt.Authentication.repository.UserRepository;
import com.example.springJwt.Authentication.service.JwtService;
import com.example.springJwt.CarRenal.model.Car;
import com.example.springJwt.CarRenal.model.EngineType;
import com.example.springJwt.CarRenal.repositories.CarRepository;
import com.example.springJwt.CarRenal.repositories.EngineTypeRepository;
import com.example.springJwt.HotelBooking.model.City;
import com.example.springJwt.HotelBooking.model.Hotel;
import com.example.springJwt.HotelBooking.repositories.CityRepository;
import com.example.springJwt.HotelBooking.repositories.HotelRepository;
import com.example.springJwt.RestaurantReservation.model.Restaurant;
import com.example.springJwt.RestaurantReservation.repositories.RestaurantRepository;
import com.example.springJwt.Authentication.service.AuthenticationService;


@Service
public class AdminService {


    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationService authService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CarRepository carRepository;
    
    @Autowired
    EngineTypeRepository engineTypeRepository;




    public boolean isAdmin(String token) {
       
        Optional<Token> optionalToken = tokenRepository.findByToken(token);
        
        if (optionalToken.isPresent()) {
            Token foundToken = optionalToken.get();
            
            if (foundToken.getUser() != null) {
                
                return foundToken.getUser().getRole() == Role.ADMIN;
            }
        }
        return false; 
    }

    public AuthenticationResponse addUser(AuthRequestDTO request) {

        // check if user already exist. if exist than authenticate the user
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }

        if(!request.getPassword().equals(request.getConfirmationPassword())) throw new RuntimeException("Password does not match");



        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(Role.valueOf(request.getRole()));

        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);
            

        
            Token token = new Token();
            token.setToken(jwt);
            token.setLoggedOut(true);
            token.setUser(user);
            tokenRepository.save(token);
        

        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    public String addRestaurant(RestaurantAddition request) {
            Restaurant restaurant =new Restaurant();
            restaurant.setName(request.getName());
            restaurant.setDescription(request.getDescription());
            restaurant.setStars(request.getStars());

            City city=cityRepository.findByCityNameIgnoreCase(request.getCityName());
            if(city!=null){ 
                restaurant.setCity(city);
            }
            restaurantRepository.save(restaurant);

            return "Restaurant added successfully";
           

        }

        public String addHotel(RestaurantAddition request) {
            Hotel hotel =new Hotel();
            hotel.setName(request.getName());
            hotel.setDescription(request.getDescription());
            hotel.setStars(request.getStars());
            
            City city=cityRepository.findByCityNameIgnoreCase(request.getCityName());
            if(city!=null){ 
                hotel.setCity(city);
            }
            hotelRepository.save(hotel);


            return "hotel added successfully";
        }

        public String addCar(CarAddition request) {
            Car car =new Car();
            car.setBrand(request.getBrand());
            car.setModel(request.getModel());
            car.setPrice(request.getPrice());
            car.setBodyType(request.getBodyType());
            EngineType engineType=engineTypeRepository.findByType(request.getEngineType());
            car.setEngineType(engineType);
            car.setYear(request.getYear());
            car.setAutomatic(request.isAutomatic());
            car.setHasAC(request.isHasAC());
            car.setSeatingCapacity(request.getSeatingCapacity());
            car.setLuggageCapacity(request.getLuggageCapacity());

            carRepository.save(car);


            return "car added successfully";
        }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<City> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public Page<Hotel> getAllHotels(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    public Page<Car> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    

  

    
}
