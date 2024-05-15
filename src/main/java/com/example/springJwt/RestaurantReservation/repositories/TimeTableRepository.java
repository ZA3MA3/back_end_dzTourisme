package com.example.springJwt.RestaurantReservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springJwt.RestaurantReservation.model.TimeTable;

import java.time.LocalTime;
import java.util.List;


public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {

    TimeTable findByTime(LocalTime localTime);
    

}
