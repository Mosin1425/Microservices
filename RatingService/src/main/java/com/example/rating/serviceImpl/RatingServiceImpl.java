package com.example.rating.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.rating.entities.Rating;
import com.example.rating.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Rating createRating(Rating rating) {
		String random_id = UUID.randomUUID().toString();
		rating.setRatingId(random_id);
		jdbcTemplate.update("INSERT INTO RATINGS(ratingId, userId, hotelId, rating, feedback) VALUES (?,?,?,?,?)",random_id,rating.getUserId(),rating.getHotelId(),rating.getRating(),rating.getFeedback());
		return rating;
	}

	@Override
	public List<Rating> getAllRatings() {
		return jdbcTemplate.query("SELECT * FROM RATINGS", (rs, rowNum) -> 
		new Rating(
				rs.getString("ratingId"),
				rs.getString("userId"),
				rs.getString("hotelId"),
				rs.getString("rating"),
				rs.getString("feedback")
				));
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		return jdbcTemplate.query("SELECT * FROM RATINGS WHERE USERID=?", (rs, rowNum) -> 
		new Rating(
				rs.getString("ratingId"),
				rs.getString("userId"),
				rs.getString("hotelId"),
				rs.getString("rating"),
				rs.getString("feedback")
				),userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		return jdbcTemplate.query("SELECT * FROM RATINGS WHERE HOTELID=?", (rs, rowNum) -> 
		new Rating(
				rs.getString("ratingId"),
				rs.getString("userId"),
				rs.getString("hotelId"),
				rs.getString("rating"),
				rs.getString("feedback")
				),hotelId);
	}
	
}
