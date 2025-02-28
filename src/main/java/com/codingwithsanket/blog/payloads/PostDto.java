package com.codingwithsanket.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codingwithsanket.blog.entities.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
//	private String imageName="default.png";
	private String imageName;
	private Date date;
	private CategoryDto category;
	private UserDto user;

	// when ever you get all post then all commnet gets

	private List<Comment> comments = new ArrayList<>();

//	

}
