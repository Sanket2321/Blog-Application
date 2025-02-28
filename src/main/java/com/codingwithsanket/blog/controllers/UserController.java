package com.codingwithsanket.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingwithsanket.blog.payloads.ApiResponse;
import com.codingwithsanket.blog.payloads.LoginRequest;
import com.codingwithsanket.blog.payloads.UserDto;
import com.codingwithsanket.blog.services.UserService;

@CrossOrigin("*") // Change to match your frontend
@RestController
@RequestMapping("api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

		UserDto createuserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createuserDto, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
		UserDto user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return new ResponseEntity<>(new ApiResponse("Invalid email or password", false), HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {

		UserDto updatedUser = this.userService.updateUser(userDto, userId);

		return ResponseEntity.ok(updatedUser);

	}

	@DeleteMapping("{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {

		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Sucessfully", true), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

}

/*
 * When Spring Boot starts, it performs auto-configuration for libraries it
 * detects on the classpath. Since springdoc-openapi-ui is part of your
 * dependencies, Spring Boot automatically applies the configurations needed for
 * OpenAPI/Swagger.
 * 
 * Internally, Spring Boot does the following:
 * 
 * Scan REST Controllers: It scans all @RestController or @Controller beans in
 * your application. Generate OpenAPI Spec: Springdoc OpenAPI reads all
 * the @RequestMapping, @GetMapping, @PostMapping, etc., annotations and
 * generates the OpenAPI specification document. Configure Swagger UI: The
 * library automatically configures Swagger UI by serving it through a URL path
 * (usually /swagger-ui.html). This allows you to interact with the API directly
 * from the browser.
 */
