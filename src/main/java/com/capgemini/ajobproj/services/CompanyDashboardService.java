package com.capgemini.ajobproj.services;

import java.util.List;

import com.capgemini.ajobproj.dto.CompanyDashboardResponse;
import com.capgemini.ajobproj.dto.JobResponse;

public interface CompanyDashboardService {
	CompanyDashboardResponse getDashboardData(Long companyId);
	List<JobResponse> getCompanyJobs(Long companyId);
	void updateApplicationStatus(Long applicationId, String status);
}
