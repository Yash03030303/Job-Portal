package com.capgemini.ajobproj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationDetail {
    private Long applicationId;
    private String jobTitle;  // Will come from Job.name
    private String companyName; // Will come from Users.name (for ADMIN users)
    private String applyDate;
    private String status;
}