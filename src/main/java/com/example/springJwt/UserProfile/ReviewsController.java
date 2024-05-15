package com.example.springJwt.UserProfile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springJwt.Authentication.model.User;
import com.example.springJwt.Authentication.repository.UserRepository;
import com.example.springJwt.HotelBooking.model.Hotel;
import com.example.springJwt.HotelBooking.model.HotelReview;
import com.example.springJwt.HotelBooking.repositories.HotelRepository;
import com.example.springJwt.RestaurantReservation.model.Restaurant;
import com.example.springJwt.RestaurantReservation.model.RestaurantReview;
import com.example.springJwt.RestaurantReservation.repositories.RestaurantRepository;
import com.example.springJwt.UserProfile.DTOs.HotelReviewResponse;
import com.example.springJwt.UserProfile.DTOs.ReviewDTO;
import com.example.springJwt.UserProfile.repositries.HotelReviewRepository;
import com.example.springJwt.UserProfile.repositries.RestaurantReviewRepository;
import com.example.springJwt.UserProfile.service.ReviewsService;

@RestController
public class ReviewsController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelReviewRepository hotelReviewRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    RestaurantReviewRepository restaurantReviewRepository;
    @Autowired
    ReviewsService reviewsService;


    @PostMapping("/postHotelReview")
    public ResponseEntity<String> postHotelReview(@RequestBody ReviewDTO request){
        HotelReview review=new HotelReview();
        review.setComment(request.getComment());
        review.setDate(LocalDate.now());
        review.setRating(request.getRating());

        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            review.setUser(user);
        }

        
        Optional<Hotel> optionalHotel = hotelRepository.findById(request.getHotelId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            review.setHotel(hotel);
        }

       hotelReviewRepository.save(review);

       return ResponseEntity.ok("hotel review added successfully.");
  
    }

    @PostMapping("/postRestaurantReview")
    public ResponseEntity<String> postReview(@RequestBody ReviewDTO request){
        RestaurantReview review=new RestaurantReview();
        review.setComment(request.getComment());
        review.setDate(LocalDate.now());
        review.setRating(request.getRating());

        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            review.setUser(user);
        }

        
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(request.getRestaurantId());
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            review.setRestaurant(restaurant);
        }

       restaurantReviewRepository.save(review);

       return ResponseEntity.ok("hotel review added successfully.");

  
    }


    
@GetMapping("/hotelReviewsList")
public ResponseEntity<HotelReviewResponse> hotelsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                            @RequestParam(name="id")  Integer hotelId ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
    Page<HotelReview> hotelReviewsPage = reviewsService.getAllHotelReviews(pageable,hotelId);

    List<ReviewDTO> hotelReviews = hotelReviewsPage.getContent().stream()
            .map(review -> new ReviewDTO(review))
            .collect(Collectors.toList());

    int totalPages = hotelReviewsPage.getTotalPages();
    int currentPage = hotelReviewsPage.getNumber();
    long totalHotels = hotelReviewsPage.getNumberOfElements();

    HotelReviewResponse hotelReviewResponse = new HotelReviewResponse(hotelReviews, totalPages, currentPage, totalHotels);
    return ResponseEntity.ok(hotelReviewResponse);
}

@GetMapping("/restaurantReviewsList")
public ResponseEntity<HotelReviewResponse> hotelReviewsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                            @RequestParam(name="id")  Integer restaurantId) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
    Page<RestaurantReview> restaurantReviewsPage = reviewsService.getAllRestaurantReviews(pageable,restaurantId);

    List<ReviewDTO> restaurantReviews = restaurantReviewsPage.getContent().stream()
            .map(review -> new ReviewDTO(review))
            .collect(Collectors.toList());

    int totalPages = restaurantReviewsPage.getTotalPages();
    int currentPage = restaurantReviewsPage.getNumber();
    long totalHotels = restaurantReviewsPage.getNumberOfElements();

    HotelReviewResponse restaurantReviewResponse = new HotelReviewResponse(restaurantReviews, totalPages, currentPage, totalHotels);
    return ResponseEntity.ok(restaurantReviewResponse);
}







}
