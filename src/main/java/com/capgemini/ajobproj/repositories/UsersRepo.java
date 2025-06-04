package com.capgemini.ajobproj.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.ajobproj.entities.Users;

public interface UsersRepo extends JpaRepository<Users, Long> {
	
	 Optional<Users> findByEmail(String email);
	 
	 Users findByUserId(Long userId);
	 
	 List<Users> findByUserIdInAndUserType(List<Long> userIds, String userType);
	 
	 @Query("SELECT u FROM Users u WHERE u.userId IN :userIds AND u.userType = 'ADMIN'")
	 List<Users> findByUserIdInAndUserType(@Param("userIds") List<Long> userIds);

	boolean existsByEmail(String email);
}
