package com.capgemini.ajobproj.services;

import java.util.List;

import com.capgemini.ajobproj.dto.PostJobDto;
import com.capgemini.ajobproj.entities.Job;

public interface JobService {
	
	List<Job> getAllJobs();
	Job createJob(PostJobDto jobDTO);
	void deleteJob(Long id);
	Job updateJob(Long id, PostJobDto jobDTO);
	Job getJobById(Long id);
	
	List<Job> getJobsByUserId(Long userId);
}
