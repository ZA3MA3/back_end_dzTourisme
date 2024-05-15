package com.example.springJwt.RestaurantReservation.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springJwt.RestaurantReservation.model.Restaurant;
import com.example.springJwt.RestaurantReservation.model.RestaurantTimeTable;

import java.time.LocalTime;
import java.util.List;
public interface RestaurantTimeTableRepository extends JpaRepository<RestaurantTimeTable, Integer> {

     /*@Query("SELECT t.time FROM RestaurantTimeTable rtt JOIN rtt.timeTable t WHERE rtt.restaurant.id = :restaurantId AND t.time >= :startTime AND t.time <= :endTime")
       List<LocalTime> findAvailableHours(@Param("restaurantId") Integer restaurantId, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime); */ 
       @Query("SELECT CASE WHEN :hour >= :startHour AND :hour <= :endHour THEN " +
       "(SELECT tt.time FROM TimeTable tt " +
       "WHERE tt.id IN (SELECT rtt.timeTable.id FROM RestaurantTimeTable rtt WHERE rtt.restaurant.id = :restaurantId) " +
       "AND tt.time >= :hour - :interval AND tt.time <= :hour + :interval ORDER BY tt.time) " +
       "ELSE NULL END " +
       "FROM Restaurant r " +
       "JOIN r.restaurantTimeTables rtt " +
       "JOIN rtt.timeTable tt " +
       "WHERE r.id = :restaurantId " +
       "GROUP BY tt.time " +
       "ORDER BY tt.time")
List<LocalTime> getAvailableHours(@Param("restaurantId") Integer restaurantId,
                                 @Param("hour") LocalTime hour,
                                 @Param("startHour") LocalTime startHour,
                                 @Param("endHour") LocalTime endHour,
                                 @Param("interval") LocalTime interval);

      RestaurantTimeTable findByRestaurantIdAndTimeTableId(Integer restaurantId, Integer timeTableId);

    List<RestaurantTimeTable> findByRestaurant(Restaurant restaurant);
}


