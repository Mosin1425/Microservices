package com.incture.user.service.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.incture.user.service.entities.Hotel;
import com.incture.user.service.entities.Rating;
import com.incture.user.service.entities.User;
import com.incture.user.service.exceptions.ResourceNotFoundException;
import com.incture.user.service.externalService.HotelService;
import com.incture.user.service.repositories.UserRepository;
import com.incture.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		List<User> users = userRepository.findAll();
		
		for(User user : users) {
			Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/ratingByUserId/" + user.getUserId(), Rating[].class);
			List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
			for(Rating rating : ratings) {
//				Calling with Rest Template
//				Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/getById/" + rating.getHotelId(), Hotel.class);
				
//				Calling using feign client
				Hotel hotel = hotelService.getHotel(rating.getHotelId());
				rating.setHotel(hotel);
			}
			user.setRatings(ratings);
		}
		return users;
		
	}
	
	@Override
	public User getUserById(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
		// URL - http://localhost:8083/ratings/ratingByUserId/091919b0-8a44-4669-bae3-9b9790c2cdb1
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/ratingByUserId/" + user.getUserId(), Rating[].class);
		logger.info("{} ",ratingsOfUser);
		
		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating ->{
			
			//API call to hotel service
			//URL - http://localhost:8082/hotels/getById/a2c54900-8aa6-43cd-91c4-a0c1472c2b28
			ResponseEntity<Hotel> hotelEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/getById/" + rating.getHotelId(), Hotel.class);
			Hotel hotel = hotelEntity.getBody();
			logger.info("resposne code: {} ",hotelEntity.getStatusCode());
			
			//set the hotel in rating
			rating.setHotel(hotel);
			logger.info("{} ",hotel);
			
			return rating;
		}).collect(Collectors.toList());
		
		
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}
}

