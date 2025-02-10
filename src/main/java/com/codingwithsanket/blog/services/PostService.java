package com.codingwithsanket.blog.services;

import java.util.List;

import com.codingwithsanket.blog.entities.Post;
import com.codingwithsanket.blog.payloads.PostDto;
import com.codingwithsanket.blog.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy);
	// get single post

	PostDto getPostById(Integer postId);

	// get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);

	// get all post by user
	List<PostDto> getPostByUser(Integer userId);

	// search post
	List<Post> searchPost(String keyword);
	

}
