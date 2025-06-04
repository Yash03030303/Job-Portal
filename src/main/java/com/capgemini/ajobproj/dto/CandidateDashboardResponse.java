package com.capgemini.ajobproj.dto;

import java.util.*;
import lombok.*;

@Getter
public class CandidateDashboardResponse {
	private int appliedJobsCount;
	private int acceptedApplicationsCount;
	private long availableJobsCount; // Changed from int to long
	private List<ApplicationDetail> applications;

	
	public CandidateDashboardResponse(int appliedJobsCount, int acceptedApplicationsCount, long availableJobsCount,
			List<ApplicationDetail> applications) {
		this.appliedJobsCount = appliedJobsCount;
		this.acceptedApplicationsCount = acceptedApplicationsCount;
		this.availableJobsCount = availableJobsCount;
		this.applications = applications;
	}
}