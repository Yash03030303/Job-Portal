package com.capgemini.ajobproj.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.ajobproj.dto.ApplicationDto;
import com.capgemini.ajobproj.dto.ApplicationResponse;
import com.capgemini.ajobproj.entities.Applications;
import com.capgemini.ajobproj.entities.Job;
import com.capgemini.ajobproj.entities.Users;
import com.capgemini.ajobproj.exception.DuplicateApplicationException;
import com.capgemini.ajobproj.repositories.ApplicationsRepo;
import com.capgemini.ajobproj.repositories.JobRepo;
import com.capgemini.ajobproj.repositories.UsersRepo;

import jakarta.transaction.Transactional;

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

	protected ApplicationsRepo repo;
	protected JobRepo jobRepo;
	protected UsersRepo usersRepo;

	@Autowired
	public ApplicationsServiceImpl(ApplicationsRepo repo, JobRepo jobRepo, UsersRepo usersRepo) {
		this.repo = repo;
		this.jobRepo = jobRepo;
		this.usersRepo = usersRepo;
	}

	@Override
	public List<Applications> getAll() {
		return repo.findAll();
	}

	@Override
	public Applications applyJob(ApplicationDto dto) {
		if (repo.existsByUserIdAndJobId(dto.getUserId(), dto.getJobId())) {
			throw new DuplicateApplicationException("User already applied to this job");
		}
		if (!jobRepo.existsById(dto.getJobId())) {
			throw new IllegalArgumentException("Job not found");
		}

		// Verify user exists
		if (!usersRepo.existsById(dto.getUserId())) {
			throw new IllegalArgumentException("User not found");
		}
		System.out.println("DTO received in service: " + dto); // Debug log
		if (dto.getUserId() == null) {
			throw new IllegalArgumentException("User ID cannot be null");
		}
		if (dto.getJobId() == null) {
			throw new IllegalArgumentException("Job ID cannot be null");
		}

		Applications application = new Applications();
		application.setUserId(dto.getUserId());
		application.setJobId(dto.getJobId());
		application.setLinkedInID(dto.getLinkedInID());
		application.setApplyDate(dto.getApplyDate());
		application.setStatus(dto.getStatus());

		System.out.println("Entity before save: " + application); // Debug log
		return repo.save(application);
	}

	@Override
	public Applications getApplicationById(Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
	}

	
	// Received Applications
	@Override
	public List<ApplicationResponse> getApplicationsForEmployer(Long employerId) {
		// 1. Get all jobs posted by this employer
		List<Job> employerJobs = jobRepo.findByUserId(employerId);
		List<Long> jobIds = employerJobs.stream().map(Job::getJobId).collect(Collectors.toList());

		// 2. Get all applications for these jobs
		List<Applications> applications = repo.findByJobIds(jobIds);

		// 3. Map to response DTO with user details
		return applications.stream().map(app -> {
			Users candidate = usersRepo.findById(app.getUserId())
					.orElseThrow(() -> new RuntimeException("User not found"));
			Job job = employerJobs.stream().filter(j -> j.getJobId().equals(app.getJobId())).findFirst()
					.orElseThrow(() -> new RuntimeException("Job not found"));
			return new ApplicationResponse(app.getId(), candidate.getName(), candidate.getEmail(), job.getName(),
					app.getStatus(), app.getApplyDate());
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateApplicationStatus(Long applicationId, String status) {
		if (!Arrays.asList("Pending", "Shortlisted", "Rejected").contains(status)) {
			throw new IllegalArgumentException("Invalid status");
		}
		repo.updateStatus(applicationId, status);
	}
}
