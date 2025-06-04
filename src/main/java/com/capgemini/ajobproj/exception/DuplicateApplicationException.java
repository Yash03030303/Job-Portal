package com.capgemini.ajobproj.exception;

public class DuplicateApplicationException extends RuntimeException{
	public DuplicateApplicationException(String message) {
        super("You have already applied to this job");
    }
}
