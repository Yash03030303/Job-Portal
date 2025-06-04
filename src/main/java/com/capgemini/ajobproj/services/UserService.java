package com.capgemini.ajobproj.services;

import java.util.List;

import com.capgemini.ajobproj.entities.Users;

public interface UserService {
	List<Users> getAllUsers();
	Users createUser(Users u);
	Users updateUser(Long id, Users u);
	
	Users findUserByEmail(String email);
	
	Users getUserById(Long userId);
}
