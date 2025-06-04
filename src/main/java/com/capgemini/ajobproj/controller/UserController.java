package com.capgemini.ajobproj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.ajobproj.entities.Users;
import com.capgemini.ajobproj.exception.DuplicateEmailException;
import com.capgemini.ajobproj.repositories.UsersRepo;
import com.capgemini.ajobproj.services.UserService;
import com.capgemini.ajobproj.dto.LoginDto;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userservice;
	private final UsersRepo repo;
	
	@Autowired
	public UserController(UserService userservice, UsersRepo repo) {
		this.userservice = userservice;
		this.repo = repo;
	}
	
	@GetMapping
	public ResponseEntity<List<Users>> getAllUsers(){
		return ResponseEntity.status(HttpStatus.OK).body(userservice.getAllUsers());
	}
	
	@PostMapping("/register")
	public ResponseEntity<Users> createUser(@RequestBody Users c) {
		Users saved = userservice.createUser(c);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> authenticatUser(@RequestBody LoginDto loginDto) {
	    Users user = userservice.findUserByEmail(loginDto.getEmail());
	    Map<String, Object> response = new HashMap<>();

	    if (user != null && user.getPassword().equals(loginDto.getPassword())) {
	        response.put("success", true);
	        response.put("message", "Login successful!");
	        response.put("user", user);
	        return ResponseEntity.ok(response);
	    } else {
	        response.put("success", false);
	        response.put("message", "Invalid email or password");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	}
	
	//Profile
    @GetMapping("/{userId}")
    public Users getUserProfile(@PathVariable Long userId) {
        return userservice.getUserById(userId);
    }
	
    
    //Email check
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailAvailability(@RequestParam String email) {
        if (repo.existsByEmail(email)) {
            throw new DuplicateEmailException("Email is already in use");
        }
        return ResponseEntity.ok().build();
    }
}
