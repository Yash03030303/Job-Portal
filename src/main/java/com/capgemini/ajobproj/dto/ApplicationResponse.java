package com.capgemini.ajobproj.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationResponse {
    private Long id;
    private String name;
    private String email;
    private String jobTitle;
    private String status;
    private String applyDate;
}