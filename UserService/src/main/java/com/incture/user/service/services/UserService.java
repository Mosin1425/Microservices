package com.incture.user.service.services;

import java.util.List;

import com.incture.user.service.entities.User;

public interface UserService {
	//save user
	User saveUser(User user);
	
	//get all users
	List<User> getAllUser();
	
	//get user by id
	User getUserById(String id);
	
	//delete user
	void deleteUser(String id);
}
