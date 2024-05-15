package com.example.springJwt.HotelBooking.DTOs;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SearchRequest {
    private String locationName;
    
  
}
