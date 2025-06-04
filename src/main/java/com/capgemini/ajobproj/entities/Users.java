package com.capgemini.ajobproj.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Table(name="users")
@Entity
public class Users {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long userId;
	private String name;
	private String location;
	private Long phone;
	private String email;
	private String password;
	private String userType;
	
	public Users(Long userId, String name, Long phone, String location, String email, String password, String userType) {
		this.userId = userId;
		this.name = name;
		this.location = location;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}
	public Users() {
		
	}
}
