package com.capgemini.ajobproj.dto;

import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor
public class CompanyDashboardResponse {
    private int totalJobs;
    private int totalApplications;
    private long shortlistedCount;
    private long rejectedCount;
    private long pendingCount;
    private List<JobResponse> recentJobs;
}