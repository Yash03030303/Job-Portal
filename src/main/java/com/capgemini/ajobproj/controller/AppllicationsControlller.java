package com.capgemini.ajobproj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.ajobproj.services.ApplicationsService;

import jakarta.validation.Valid;

import com.capgemini.ajobproj.dto.ApplicationDto;
import com.capgemini.ajobproj.dto.ApplicationResponse;
import com.capgemini.ajobproj.entities.Applications;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/applications")
public class AppllicationsControlller {
	ApplicationsService service;
	
	@Autowired
	public AppllicationsControlller(ApplicationsService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Applications> getAllApplications() {
	    return service.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Applications> getApplicationById(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(service.getApplicationById(id));
	}
	
	@PostMapping
	public ResponseEntity<Applications> applyJob(@Valid @RequestBody ApplicationDto a){
		System.out.println("Received application: " + a);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.applyJob(a));
	}
	
	
	//Received Applications
	@GetMapping("/app/{id}")
    public ResponseEntity<List<ApplicationResponse>> getEmployerApplications(@PathVariable Long id) {
        return ResponseEntity.ok(service.getApplicationsForEmployer(id));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateApplicationStatus(@PathVariable Long id,@RequestParam String status) {
        service.updateApplicationStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
