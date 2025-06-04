package com.capgemini.ajobproj.dto;

public class PostJobDto {
	private String name;
	private Long salary;
	private String location;
	private String description;
	private String jobType;
	private Long userId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSalary() {
		return salary;
	}
	public void setSalary(Long salary) {
		this.salary = salary;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "PostJobDto [name=" + name + ", salary=" + salary + ", location=" + location + ", description="
				+ description + ", jobType=" + jobType + ", userId=" + userId + "]";
	}
	
	
}
