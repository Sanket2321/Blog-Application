package com.codingwithsanket.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codingwithsanket.blog.payloads.ApiResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j // Enables logging
public class GlobalExceptionHandler {

	// Handle ResourceNotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.error("Resource Not Found: {}", ex.getMessage());
		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	// Handle Validation Errors
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			errors.put(fieldName, errorMessage);
		}

		log.warn("Constraint Violations: {}", errors);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// Handle Generic Exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
		log.error("Unexpected Error: {}", ex.getMessage());
		ApiResponse apiResponse = new ApiResponse("An unexpected error occurred!", false);
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
