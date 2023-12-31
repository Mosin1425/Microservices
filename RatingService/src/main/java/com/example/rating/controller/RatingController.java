package com.example.rating.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rating.entities.Rating;
import com.example.rating.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/createRating")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating){
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createRating(rating));
	}
	
	@GetMapping("/getAllRatings")
	public ResponseEntity<List<Rating>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRatings());
	}
	
	@GetMapping("/ratingByUserId/{userId}")
	public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId){
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingByUserId(userId));
	}
	
	@GetMapping("/ratingByHotelId/{HotelId}")
	public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable String HotelId){
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingByHotelId(HotelId));
	}
}
