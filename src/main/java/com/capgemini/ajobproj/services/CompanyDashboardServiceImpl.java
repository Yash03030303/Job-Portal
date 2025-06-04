package com.capgemini.ajobproj.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.*;

import com.capgemini.ajobproj.dto.CompanyDashboardResponse;
import com.capgemini.ajobproj.dto.JobResponse;
import com.capgemini.ajobproj.entities.Applications;
import com.capgemini.ajobproj.entities.Job;
import com.capgemini.ajobproj.repositories.ApplicationsRepo;
import com.capgemini.ajobproj.repositories.JobRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyDashboardServiceImpl implements CompanyDashboardService {

    private final JobRepo jobRepo;
    private final ApplicationsRepo applicationsRepo;

    @Override
    public CompanyDashboardResponse getDashboardData(Long companyId) {
        // 1. Get company's posted jobs
        List<Job> companyJobs = jobRepo.findByUserId(companyId);
        
        // 2. Get all applications for these jobs
        List<Long> jobIds = companyJobs.stream().map(Job::getJobId).collect(Collectors.toList());
        List<Applications> applications = applicationsRepo.findByJobIds(jobIds);
        
        // 3. Count applications by status
        Map<String, Long> statusCounts = applications.stream()
            .collect(Collectors.groupingBy(
                app -> normalizeStatus(app.getStatus()),
                Collectors.counting()
            ));
        
        // Ensure all status categories exist in the map
        statusCounts.putIfAbsent("Shortlisted", 0L);
        statusCounts.putIfAbsent("Rejected", 0L);
        statusCounts.putIfAbsent("Pending", 0L);
        
        // 4. Get recent jobs (last 5 posted)
        List<Job> recentJobs = companyJobs.stream()
            .sorted(Comparator.comparing(Job::getJobId).reversed())
            .limit(5)
            .collect(Collectors.toList());
        
        // 5. Create response DTO
        return new CompanyDashboardResponse(
            companyJobs.size(),
            applications.size(),
            statusCounts.get("Shortlisted"),
            statusCounts.get("Rejected"),
            statusCounts.get("Pending"),
            recentJobs.stream().map(this::convertToJobResponse).collect(Collectors.toList())
        );
    }

    @Override
    public List<JobResponse> getCompanyJobs(Long companyId) {
        return jobRepo.findByUserId(companyId).stream()
            .map(this::convertToJobResponse)
            .collect(Collectors.toList());
    }

    @Override
    public void updateApplicationStatus(Long applicationId, String status) {
        if (!List.of("Pending", "Shortlisted", "Rejected").contains(status)) {
            throw new IllegalArgumentException("Invalid status value");
        }
        applicationsRepo.updateStatus(applicationId, status);
    }

    private String normalizeStatus(String status) {
        if (status == null) return "Pending";
        status = status.toLowerCase();
        if (status.contains("shortlist")) return "Shortlisted";
        if (status.contains("reject")) return "Rejected";
        return "Pending";
    }

    private JobResponse convertToJobResponse(Job job) {
        return new JobResponse(
            job.getJobId(),
            job.getName(),
            job.getLocation(),
            job.getJobType(),
            job.getSalary()
        );
    }

}