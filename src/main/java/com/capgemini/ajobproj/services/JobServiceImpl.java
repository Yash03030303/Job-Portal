package com.capgemini.ajobproj.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.ajobproj.dto.PostJobDto;
import com.capgemini.ajobproj.entities.Job;
import com.capgemini.ajobproj.repositories.JobRepo;
import com.capgemini.ajobproj.repositories.UsersRepo;

@Service
public class JobServiceImpl implements JobService{
	private JobRepo jobRepo;
	private UsersRepo usersRepo;
	
	@Autowired
	public JobServiceImpl(JobRepo jobRepo, UsersRepo usersRepo) {
		this.jobRepo = jobRepo;
		this.usersRepo = usersRepo;
	} 
	
	public List<Job> getAllJobs() {
        return jobRepo.findAll();
    }
	
	public Job createJob(PostJobDto jobDTO) {
        if (!usersRepo.existsById(jobDTO.getUserId())) {
            throw new IllegalArgumentException("User not found");
        }

        Job job = new Job();
        job.setName(jobDTO.getName());
        job.setSalary(jobDTO.getSalary());
        job.setLocation(jobDTO.getLocation());
        job.setDescription(jobDTO.getDescription());
        job.setJobType(jobDTO.getJobType());
        job.setUserId(jobDTO.getUserId());

        return jobRepo.save(job);
    }
	
	public Job updateJob(Long id, PostJobDto jobDTO) {
        Job existingJob = jobRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        existingJob.setName(jobDTO.getName());
        existingJob.setSalary((long) jobDTO.getSalary());
        existingJob.setLocation(jobDTO.getLocation());
        existingJob.setDescription(jobDTO.getDescription());
        existingJob.setJobType(jobDTO.getJobType());

        return jobRepo.save(existingJob);
    }

	public Job getJobById(Long id) {
	    return jobRepo.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
	}
	
    public void deleteJob(Long id) {
        Job job = jobRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        jobRepo.delete(job);
    }
    
    //chart
    public List<Job> getJobsByUserId(Long userId) {
        return jobRepo.findByUserId(userId);
    }
}
