package com.capgemini.ajobproj.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.ajobproj.entities.Users;
import com.capgemini.ajobproj.exception.DuplicateEmailException;
import com.capgemini.ajobproj.repositories.UsersRepo;

@Service
public class UserServiceImpl implements UserService{
	private final UsersRepo repo;
	
	@Autowired
	public UserServiceImpl(UsersRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public List<Users> getAllUsers(){
		return repo.findAll();
	}
	
	@Override
	public Users createUser(Users user) {
	    // Check if email already exists
	    if (repo.existsByEmail(user.getEmail())) {
	        throw new DuplicateEmailException("Email " + user.getEmail() + " is already in use");
	    }
	    return repo.save(user);
	}

	@Override
	public Users updateUser(Long id, Users updated) {
		Optional<Users> optional = repo.findById(id);
		if (optional.isPresent()) {
			Users existing = optional.get();
			existing.setName(updated.getName());
			existing.setPhone(updated.getPhone());
			existing.setEmail(updated.getEmail());
			existing.setPassword(updated.getPassword());
			return repo.save(existing);
		}
		return null;
	}

	public Users findUserByEmail(String email) {
        Optional<Users> userOptional = repo.findByEmail(email);
        return userOptional.orElse(null); 
    }
	
	
	//Profile
	public Users getUserById(Long userId) {
        return repo.findByUserId(userId);
    }
}
