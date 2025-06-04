package com.capgemini.ajobproj.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.ajobproj.dto.PostJobDto;
import com.capgemini.ajobproj.entities.Job;
import com.capgemini.ajobproj.services.JobService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/api/postjob")
public class JobController {
	public JobService service;

	@Autowired
	public JobController(JobService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Job> createJob(@RequestBody PostJobDto jobDTO) {

		Job job = service.createJob(jobDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(job);
	}

	@GetMapping
	public List<Job> getAllJobs() {
		return service.getAllJobs();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable Long id) {
		Job job = service.getJobById(id);
		return ResponseEntity.ok(job);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody PostJobDto jobDTO) {
		Job updatedJob = service.updateJob(id, jobDTO);
		return ResponseEntity.ok(updatedJob);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
		service.deleteJob(id);
		return ResponseEntity.noContent().build();
	}

	// Chart1 in Company
	@GetMapping("/user/{userId}")
	public List<Job> getJobsByUserId(@PathVariable Long userId) {
		return service.getJobsByUserId(userId);
	}

	@GetMapping("/stats/{userId}")
	public Map<String, Long> getJobTypeStatistics(@PathVariable Long userId) {
		List<Job> jobs = service.getJobsByUserId(userId);
		return jobs.stream().collect(Collectors.groupingBy(Job::getJobType, Collectors.counting()));
	}

	// Chart Candidate:
	@GetMapping("/by-location")
	public Map<String, Long> getJobsByLocation() {
		List<Job> jobs = service.getAllJobs();

		return jobs.stream().collect(Collectors.groupingBy(Job::getLocation, Collectors.counting()));
	}
}
