package com.codingwithsanket.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingwithsanket.blog.entities.User;
import com.codingwithsanket.blog.exceptions.ResourceNotFoundException;
import com.codingwithsanket.blog.payloads.UserDto;
import com.codingwithsanket.blog.repositories.UserRepo;
import com.codingwithsanket.blog.services.UserService;
@Service
public class UserServiceimpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		// Check if the email already exists
	    if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
	        throw new RuntimeException("Email already exists!"); // Or use a custom exception
	    }
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);

	}
	
	@Override
	public UserDto authenticateUser(String email, String password) {
	    User user = userRepo.findByEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

	    if (!user.getPassword().equals(password)) {
	        throw new RuntimeException("Invalid credentials! Please check your email or password.");
	    }

	    return this.userToDto(user); // Convert User entity to UserDto
	}


	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getPassword());
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-gen erated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.userToDto(user);

	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();

		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}

	/*
	 * dtoToUser: Converts API input into a format the database can understand.
	 * userToDto: Converts database data into a format the API can send to clients.
	 * dtoToUser(UserDto userDto) This method converts a UserDto object into a User
	 * object.
	 * 
	 * Meaning: When data comes from the user (e.g., via an API request), it is
	 * often received as a UserDto object. However, the database does not understand
	 * UserDto because the database deals with the User entity. So, we need to
	 * convert the DTO into the entity before saving it to the database.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//so basically modelmapper used for convert to class object to another class 
		//we need to passed source and second kis class me like user.class
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user,UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;

	}

}
