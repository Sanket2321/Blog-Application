package com.codingwithsanket.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codingwithsanket.blog.entities.Post;
import com.codingwithsanket.blog.entities.User;
import com.codingwithsanket.blog.exceptions.ResourceNotFoundException;
import com.codingwithsanket.blog.payloads.PostDto;
import com.codingwithsanket.blog.payloads.PostResponse;
import com.codingwithsanket.blog.repositories.CategoryRepo;
import com.codingwithsanket.blog.repositories.PostRepo;
import com.codingwithsanket.blog.repositories.UserRepo;
import com.codingwithsanket.blog.services.PostService;

@Service

public class PostServiceimpl implements PostService {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "User id", userId));
		com.codingwithsanket.blog.entities.Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);
		// TODO Auto-generated method stub
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// Fetch the existing post from the repository
		Post existingPost = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		// Update the post with new details
		existingPost.setTitle(postDto.getTitle());
		existingPost.setContent(postDto.getContent());
		existingPost.setImageName(postDto.getImageName());
		existingPost.setDate(new Date()); // Update the date to the current date

		// Save the updated post to the repository
		Post updatedPost = this.postRepo.save(existingPost);

		// Map the updated post to PostDto
		PostDto updatedPostDto = this.modelMapper.map(updatedPost, PostDto.class);

		return updatedPostDto;
	}

	@Override
	public void deletePost(Integer postId) {
		// Fetch the post from the repository
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		// Delete the post
		this.postRepo.delete(post);
	}

	/*
	 * In the service layer, I implemented the getAllPost method to handle
	 * pagination.
	 * 
	 * I used the PageRequest.of(pageNumber, pageSize) method to create a Pageable
	 * object, which specifies the page number and page size.
	 * 
	 * I called the findAll(Pageable pageable) method of the PostRepo repository,
	 * which returns a Page<Post> object containing the paginated results.
	 * 
	 * I used the getContent() method of the Page<Post> object to get the list of
	 * posts for the specified page.
	 * 
	 * Finally, I mapped the list of posts to PostDto objects and returned it.
	 */

	/*
	 * Pageable and Page: I utilized Spring Data JPA's Pageable and Page classes to
	 * handle pagination.
	 * 
	 * Request Parameters: I used @RequestParam to accept pageNumber and pageSize as
	 * optional query parameters.
	 * 
	 * Zero-Based Index: I adjusted the page number by subtracting 1 since Spring
	 * Data JPA uses zero-based page indexing.
	 */
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {

		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		Page<Post> pagePost = this.postRepo.findAll(p);
		// Fetch all posts from the repository
		List<Post> allposts = pagePost.getContent();

		// Map the posts to PostDto objects
		List<PostDto> postDtos = allposts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// Fetch the post from the repository
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		// Map the post to PostDto
		PostDto postDto = this.modelMapper.map(post, PostDto.class);

		return postDto;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		com.codingwithsanket.blog.entities.Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		com.codingwithsanket.blog.entities.User cat = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> posts = this.postRepo.findByUser(cat);
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDto;

		// TODO Auto-generated method stub

	}

	@Override
	public List<Post> searchPost(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
