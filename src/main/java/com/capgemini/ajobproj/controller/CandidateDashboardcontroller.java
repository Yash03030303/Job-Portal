package com.capgemini.ajobproj.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.ajobproj.dto.CandidateDashboardResponse;
import com.capgemini.ajobproj.services.CandidateDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidate-dashboard")
@RequiredArgsConstructor
public class CandidateDashboardcontroller {
	
    private final CandidateDashboardService dashboardService;

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateDashboardResponse> getDashboardData(
            @PathVariable Long candidateId) {
        CandidateDashboardResponse response = dashboardService.getDashboardData(candidateId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/applications/{applicationId}/status")
    public ResponseEntity<Void> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {
        dashboardService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/applications/{applicationId}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long applicationId) {
        dashboardService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}