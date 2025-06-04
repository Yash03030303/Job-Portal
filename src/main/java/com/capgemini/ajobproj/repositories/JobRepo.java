package com.capgemini.ajobproj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.ajobproj.entities.Job;

public interface JobRepo extends JpaRepository<Job, Long>{
	List<Job> findByUserId(Long userId);
}
