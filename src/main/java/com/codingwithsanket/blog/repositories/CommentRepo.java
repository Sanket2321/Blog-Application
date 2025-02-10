package com.codingwithsanket.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codingwithsanket.blog.entities.Comment;
@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {
	

}
