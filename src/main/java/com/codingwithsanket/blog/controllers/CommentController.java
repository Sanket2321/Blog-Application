package com.codingwithsanket.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codingwithsanket.blog.payloads.ApiResponse;
import com.codingwithsanket.blog.payloads.CommentDto;
import com.codingwithsanket.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// Create a new comment
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,@PathVariable Integer postId) {
		CommentDto createdComment = this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
	}

	// Delete a comment by ID
	@DeleteMapping("/{commentId}")
	public ApiResponse deleteComment(@PathVariable Integer commentId) {
		commentService.deleteComment(commentId);
		return new ApiResponse("post deleted successfully", true);
	}
}
