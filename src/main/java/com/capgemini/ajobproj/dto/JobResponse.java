package com.capgemini.ajobproj.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class JobResponse {
    private Long jobId;
    private String name;
    private String location;
    private String jobType;
    private Long salary;
}