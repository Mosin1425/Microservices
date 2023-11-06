package com.example.rating.service;

import java.util.List;

import com.example.rating.entities.Rating;

public interface RatingService {
	//create
	Rating createRating(Rating rating);
	
	//get all ratings
	List<Rating> getAllRatings();
	
	//get rating by userId
	List<Rating> getRatingByUserId(String userId);
	
	//get rating by hotelId
	List<Rating> getRatingByHotelId(String hotelId);
}
