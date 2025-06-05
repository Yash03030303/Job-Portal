package com.capgemini.ajobproj.dto;

import lombok.*;

@Getter 
@Setter
public class ApplicationDto {
    private Long userId;  
    private Long jobId;   
    private String linkedInID;
    private String applyDate;
    private String status;
}
