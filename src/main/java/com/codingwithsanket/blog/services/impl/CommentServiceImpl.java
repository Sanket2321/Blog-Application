package com.codingwithsanket.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingwithsanket.blog.entities.Comment;
import com.codingwithsanket.blog.entities.Post;
import com.codingwithsanket.blog.exceptions.ResourceNotFoundException;
import com.codingwithsanket.blog.payloads.CommentDto;
import com.codingwithsanket.blog.repositories.CommentRepo;
import com.codingwithsanket.blog.repositories.PostRepo;
import com.codingwithsanket.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		// Retrieve the post
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

		// Convert CommentDto to Comment entity
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);

		// Save the comment
		Comment savedComment = commentRepo.save(comment);

		// Convert saved Comment to CommentDto
		CommentDto savedCommentDto = modelMapper.map(savedComment, CommentDto.class);

		return savedCommentDto;
	}

	@Override
	public void deleteComment(Integer commentId) {
		// Retrieve the comment
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

		// Delete the comment
		commentRepo.delete(comment);
	}

}
