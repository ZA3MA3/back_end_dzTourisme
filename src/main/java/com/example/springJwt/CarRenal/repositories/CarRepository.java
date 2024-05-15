package com.example.springJwt.CarRenal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springJwt.CarRenal.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

  //  List<Car> findAllByCities_CityName(String cityName);
  
  List<Car> findAllByLocations_Name(String locationName);



    
}
