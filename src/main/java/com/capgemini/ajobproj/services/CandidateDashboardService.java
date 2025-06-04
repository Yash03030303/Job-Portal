package com.capgemini.ajobproj.services;

import com.capgemini.ajobproj.dto.CandidateDashboardResponse;

public interface CandidateDashboardService {
	CandidateDashboardResponse getDashboardData(Long candidateId);
    void updateApplicationStatus(Long applicationId, String status);
    void deleteApplication(Long applicationId);
}
