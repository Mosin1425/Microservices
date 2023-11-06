package com.example.hotel.service.services;

import java.util.List;


import com.example.hotel.service.entities.Hotel;

public interface  HotelService {
	Hotel create(Hotel hotel);
	List<Hotel> getAll();
	Hotel getById(String id);
}
