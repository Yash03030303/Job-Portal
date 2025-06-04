package com.capgemini.ajobproj.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.capgemini.ajobproj.repositories.ApplicationsRepo;
import com.capgemini.ajobproj.repositories.JobRepo;
import com.capgemini.ajobproj.repositories.UsersRepo;
import com.capgemini.ajobproj.dto.ApplicationDetail;
import com.capgemini.ajobproj.entities.*;
import com.capgemini.ajobproj.dto.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class CandidateDashboardServiceImpl implements CandidateDashboardService {
	ApplicationsRepo applicationsRepo;
	JobRepo jobRepo;
	UsersRepo userRepo;
	
	@Autowired
	public CandidateDashboardServiceImpl(ApplicationsRepo applicationsRepo, JobRepo jobRepo, UsersRepo userRepo) {
		this.applicationsRepo = applicationsRepo;
		this.jobRepo = jobRepo;
		this.userRepo = userRepo;
	}

	@Override
	public CandidateDashboardResponse getDashboardData(Long candidateId) {
		// 1. Get all applications for this candidate
		List<Applications> applications = applicationsRepo.findByUserId(candidateId);
		if (applications == null || applications.isEmpty()) {
		    return new CandidateDashboardResponse(0, 0, 0, Collections.emptyList());
		}

		// 2. Get all jobs and companies (admins) for these applications
		List<Long> jobIds = applications.stream().map(Applications::getJobId).collect(Collectors.toList());

		// Get jobs and map them by ID
		Map<Long, Job> jobs = jobRepo.findAllById(jobIds).stream().collect(Collectors.toMap(Job::getJobId, Function.identity()));

		// Get company names (from Users table where userType = ADMIN)
		List<Long> companyIds = jobs.values().stream().map(Job::getUserId).collect(Collectors.toList());

		Map<Long, Users> companies = userRepo.findByUserIdInAndUserType(companyIds, "ADMIN").stream()
				.collect(Collectors.toMap(Users::getUserId, Function.identity()));

		// 3. Map to response DTO
		List<ApplicationDetail> applicationDetails = applications.stream().map(app -> {
			Job job = jobs.get(app.getJobId());
			Users company = job != null ? companies.get(job.getUserId()) : null;
			return new ApplicationDetail(app.getId(), job != null ? job.getName() : "N/A", // Job title from Job.name
					company != null ? company.getName() : "N/A", // Company name from Users.name
					app.getApplyDate(), app.getStatus());
		}).collect(Collectors.toList());

		// 4. Calculate statistics
		long availableJobsCount = jobRepo.count();
		long acceptedApps = applications.stream().filter(app -> List.of("Shortlisted").contains(app.getStatus())).count();
		return new CandidateDashboardResponse(
		        applications.size(),          // int
		        (int) acceptedApps,           // cast long to int
		        availableJobsCount,           // long
		        applicationDetails            // List<ApplicationDetail>
		    );
		}

	@Override
	public void updateApplicationStatus(Long applicationId, String status) {
		if (!List.of("Pending", "Reviewed", "Rejected", "Selected").contains(status)) {
			throw new IllegalArgumentException("Invalid status value");
		}
		applicationsRepo.updateStatus(applicationId, status);
	}

	@Override
	public void deleteApplication(Long applicationId) {
		applicationsRepo.deleteById(applicationId);
	}
}