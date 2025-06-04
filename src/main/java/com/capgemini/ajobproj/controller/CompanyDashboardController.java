package com.capgemini.ajobproj.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.ajobproj.dto.CompanyDashboardResponse;
import com.capgemini.ajobproj.dto.JobResponse;
import com.capgemini.ajobproj.services.CompanyDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/company-dashboard")
@RequiredArgsConstructor
public class CompanyDashboardController {

    private final CompanyDashboardService dashboardService;

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDashboardResponse> getDashboardData(@PathVariable Long companyId) {
        CompanyDashboardResponse response = dashboardService.getDashboardData(companyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/jobs/{companyId}")
    public ResponseEntity<List<JobResponse>> getCompanyJobs(@PathVariable Long companyId) {
        List<JobResponse> jobs = dashboardService.getCompanyJobs(companyId);
        return ResponseEntity.ok(jobs);
    }

    @PatchMapping("/applications/{applicationId}/status")
    public ResponseEntity<Void> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {
        dashboardService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok().build();
    }
}