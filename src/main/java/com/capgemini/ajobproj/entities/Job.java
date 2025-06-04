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
@Table(name="job")
@Entity
public class Job {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long jobId;
	private String name;
	private Long salary;
	private String location;
	private String description;
	private String jobType;
	private Long userId;
	
	public Job() {
		
	}

	public Job(Long jobId, String name, Long salary, String location, String description, String jobType, Long userId) {
		super();
		this.jobId = jobId;
		this.name = name;
		this.salary = salary;
		this.location = location;
		this.description = description;
		this.jobType = jobType;
		this.userId = userId;
	}
}
