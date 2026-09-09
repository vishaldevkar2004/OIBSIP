package com.library.service;

import java.util.List;

import com.library.entity.User;

public interface UserService {

	User registerUser(User user);
	
	User getUserById(Integer userId);
	
	User getUserByEmail(String email);
	
	List<User> getAllUsers();
	
	void deleteUser(Integer userId);
}
