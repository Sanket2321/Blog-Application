package com.codingwithsanket.blog.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codingwithsanket.blog.config.AppConstants;
import com.codingwithsanket.blog.payloads.ApiResponse;
import com.codingwithsanket.blog.payloads.PostDto;
import com.codingwithsanket.blog.payloads.PostResponse;
import com.codingwithsanket.blog.services.FileService;
import com.codingwithsanket.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	// create

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;

	@Value("${project.image}")

	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// get post by userid
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser, HttpStatus.OK);

	}

	// get post by category

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postByCategory = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postByCategory, HttpStatus.OK);

	}

	/*
	 * I added a GET endpoint (/allposts) in the PostController that accepts
	 * pageNumber and pageSize as request parameters. These parameters are optional,
	 * with default values of 1 and 5, respectively.
	 * 
	 * The endpoint calls the getAllPost method in the service layer, passing the
	 * pageNumber and pageSize parameters.
	 */

	// get all post
	@GetMapping("/allposts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumer", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	// get post by id

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postById = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

	// delete post by id
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		// Call the service method to delete the post
		this.postService.deletePost(postId);

		// Return a response indicating successful deletion
		return new ApiResponse("post deleted successfully", true);
	}

	// update post by using id

	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		// Call the service method to update the post
		PostDto updatedPost = this.postService.updatePost(postDto, postId);

		// Return the updated PostDto wrapped in a ResponseEntity with an HTTP OK status
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}

	public String getMethodName(@RequestParam String param) {
		return new String();
	}

	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {

		PostDto postById = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postById.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postById, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/*
	 * This method handles the HTTP POST request for uploading an image to a
	 * specific post.
	 * 
	 * @PostMapping("/post/image/upload/{postId}"): Maps the HTTP POST request to
	 * this method.
	 * 
	 * @RequestParam("image") MultipartFile image: Captures the uploaded image file.
	 * 
	 * @PathVariable Integer postId: Captures the postId from the URL.
	 * 
	 * @Value("${project.image}"): Injects the value of project.image from the
	 * properties file into the path variable.
	 */

}
