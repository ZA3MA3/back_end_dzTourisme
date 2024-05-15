package com.example.springJwt.CarRenal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springJwt.CarRenal.repositories.RentalRepository;

import jakarta.transaction.Transactional;

import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.Authentication.repository.UserRepository;
import com.example.springJwt.CarRenal.DTOs.RentalRequest;
import com.example.springJwt.CarRenal.DTOs.RentalsList;
import com.example.springJwt.CarRenal.model.Car;
import com.example.springJwt.CarRenal.repositories.ProtectionRepository;
import com.example.springJwt.CarRenal.model.Protection;
import com.example.springJwt.CarRenal.model.Rental;
import com.example.springJwt.CarRenal.repositories.CarRepository;
import com.example.springJwt.CarRenal.repositories.PickUpLocationRepository;


@Service
public class RentalService {
    

    @Autowired
    private  CarRepository carRepository;
    @Autowired
    private  RentalRepository rentalRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ProtectionRepository protectionRepository;
    @Autowired
    private PickUpLocationRepository pickUpLocationRepository;


    public List<Car> findAvailableCars(String locationName, LocalDate pickUpDate, LocalDate dropOffDate) {
        List<Car> allCarsInLocation = carRepository.findAllByLocations_Name(locationName);
        List<Rental> rentalsInLocation = rentalRepository.findAllByCar_Locations_Name(locationName);

        return allCarsInLocation.stream()
                .filter(car -> {
                    // Check if car has no rentals
                    boolean hasNoRentals = rentalsInLocation.stream()
                            .noneMatch(rental -> rental.getCar().getId().equals(car.getId()));
                    
                    if (hasNoRentals) {
                        return true; // Car has no rentals
                    } else {
                        // Check if the rental period doesn't overlap with the specified dates
                        return rentalsInLocation.stream()
                                .filter(rental -> rental.getCar().getId().equals(car.getId()))
                                .allMatch(rental -> pickUpDate.isAfter(rental.getDropOff()) || dropOffDate.isBefore(rental.getPickUp()));
                    }
                })
                .collect(Collectors.toList());
    }



    @Transactional
    public void rentCar(RentalRequest rentalRequest) {
        // Create a new Booking entity
        Rental rental = new Rental();
        rental.setPickUp(rentalRequest.getPickUpDate());
        rental.setPickUpTime(rentalRequest.getPickUpTime());
        rental.setDropOff(rentalRequest.getDropOffDate());
        rental.setDropOffTime(rentalRequest.getDropOffTime());
        rental.setAdditionalDriver(rentalRequest.getAdditionalDriver());
        rental.setNavigationalSystem(rentalRequest.getNavigationalSystem());
        rental.setBabySeat(rentalRequest.getBabySeat());
        rental.setChildSeat(rentalRequest.getChildSeat());
        rental.setChildBoosterSeat(rentalRequest.getChildBoosterSeat());
        rental.setLocationName(rentalRequest.getQuery());

        // Retrieve user by their token
        User user = userRepository.findByTokens_Token(rentalRequest.getToken());
        if (user == null) {
            // Handle the case where the user is not found by the token
            throw new RuntimeException("User not found for the provided token");
        }

        // Update user details
        user.setFirstname(rentalRequest.getFirstName());
        user.setLastname(rentalRequest.getLastName());
        user.setPhoneNumber(rentalRequest.getPhoneNumber());

        // Save/update the user
        userRepository.save(user);

        // Set the user for the booking
        rental.setUser(user);

        Optional<Car> optionalCar = carRepository.findById(rentalRequest.getCarId());
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            rental.setCar(car);
        }else{   
            // Handle the case where the user is not found by the token
            throw new RuntimeException("car not found for the provided id");
        }

        Optional<Protection> optionalProtection = protectionRepository.findById(rentalRequest.getProtection());
        if (optionalProtection.isPresent()) {
            Protection protection = optionalProtection.get();
            rental.setProtection(protection);
        }else{   
            // Handle the case where the user is not found by the token
            throw new RuntimeException("protection not found for the provided id");
        }

        rentalRepository.save(rental);

        
       
    }




    public List<RentalsList> rentalsList(String token) {
       
        User user = userRepository.findByTokens_Token(token);
        if (user == null) {
            // Handle the case where the user is not found by the token
            throw new RuntimeException("User not found for the provided token");
        }


        // Get reservations for the user
        List<Rental> rentals = rentalRepository.findByUser(user);

        // Prepare DTOs for response
        List<RentalsList> rentalsList = new ArrayList<>();
        for (Rental rental : rentals) {
            RentalsList dto = new RentalsList();
            dto.setRentalId(rental.getId());
            dto.setPickUp(rental.getPickUp());
            dto.setPickUpTime(rental.getPickUpTime());
            dto.setDropOff(rental.getDropOff());
            dto.setDropOffTime(rental.getDropOffTime());
            dto.setNavigationalSystem(rental.getNavigationalSystem());
            dto.setChildSeat(rental.getChildSeat());
            dto.setChildBoosterSeat(rental.getChildBoosterSeat());
            dto.setBabySeat(rental.getBabySeat());
            dto.setLocationName(rental.getLocationName());
            dto.setUserId(user.getId());
        

            Car car = rental.getCar();
            if (car != null) {
                dto.setCarId(car.getId());
                dto.setCarBrand(car.getBrand());
                dto.setCarModel(car.getModel());
                dto.setCarBodyType(car.getBodyType());
                dto.setEngineType(car.getEngineType().getType()); 
            }
    
            Protection protection = rental.getProtection();
            if (protection != null) {
                dto.setProtectionId(protection.getId());
                dto.setProtection(protection.getProtectionType());
            }

            rentalsList.add(dto);

        }
        return rentalsList;
    }



   
    public void editRental(RentalsList request) {
        Optional<Rental> optionalRental = rentalRepository.findById(request.getRentalId());
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            rental.setPickUp(request.getPickUp());
            rental.setPickUpTime(request.getPickUpTime());
            rental.setDropOff(request.getDropOff());
            rental.setDropOffTime(request.getDropOffTime());
            rental.setAdditionalDriver(request.getAdditionalDriver());
            rental.setNavigationalSystem(request.getNavigationalSystem());
            rental.setBabySeat(request.getBabySeat());
            rental.setChildSeat(request.getChildSeat());
            rental.setChildBoosterSeat(request.getChildBoosterSeat());
            rental.setLocationName(request.getLocationName());

            Optional<Protection> OptionalProtection=protectionRepository.findById(request.getProtectionId());
            if (OptionalProtection.isPresent()) {
                Protection protection=OptionalProtection.get();
                rental.setProtection(protection);
            }
            
            Optional<Car> OptionalCar=carRepository.findById(request.getCarId());
            if (OptionalCar.isPresent()) {
                Car car=OptionalCar.get();
                rental.setCar(car);
            }

            
            Optional<User> OptionalUser=userRepository.findById(request.getUserId());
            if (OptionalUser.isPresent()) {
            
            User user = OptionalUser.get();
            user.setFirstname(request.getFirstName());
            user.setLastname(request.getLastName());
            user.setPhoneNumber(request.getPhoneNumber());

            rental.setUser(user);
            }

            rentalRepository.save(rental);
        } else {
            throw new IllegalArgumentException("Reservation with ID " + request.getRentalId() + " not found");
        }
    }


}
