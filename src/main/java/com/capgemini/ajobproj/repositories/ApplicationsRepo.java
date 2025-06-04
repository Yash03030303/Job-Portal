package com.capgemini.ajobproj.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.ajobproj.entities.Applications;

public interface ApplicationsRepo extends JpaRepository<Applications, Long>{
	boolean existsByUserIdAndJobId(Long userId, Long jobId);
	
	
	//Received Applications
	@Query("SELECT a FROM Applications a WHERE a.jobId IN :jobIds")
    List<Applications> findByJobIds(@Param("jobIds") List<Long> jobIds);
    
    @Modifying
    @Query("UPDATE Applications a SET a.status = :status WHERE a.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") String status);

    @Query("SELECT a FROM Applications a WHERE a.userId = :userId ORDER BY a.applyDate DESC")
    List<Applications> findByUserId(@Param("userId") Long userId);
    
}
