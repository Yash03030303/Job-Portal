package com.capgemini.ajobproj.services;

import java.util.List;

import com.capgemini.ajobproj.dto.ApplicationDto;
import com.capgemini.ajobproj.dto.ApplicationResponse;
import com.capgemini.ajobproj.entities.Applications;

public interface ApplicationsService {
	Applications applyJob(ApplicationDto a);
	
	Applications getApplicationById(Long id);
	
	List<Applications> getAll();
	
	
	//Received Applications
	List<ApplicationResponse> getApplicationsForEmployer(Long id);
	void updateApplicationStatus(Long id, String status);
}
