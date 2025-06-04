package com.capgemini.ajobproj.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Table(name="Applications")
@Entity
public class Applications {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long jobId;
	private String linkedInID;
	private String applyDate;
	private String status;
	
	public Applications(Long id, Long userId, Long jobId, String linkedInID, String applyDate, String status) {
		this.id = id;
		this.userId = userId;
		this.jobId = jobId;
		this.linkedInID = linkedInID;
		this.applyDate = applyDate;
		this.status = status;
	}

	public Applications() {
		
	}
}
