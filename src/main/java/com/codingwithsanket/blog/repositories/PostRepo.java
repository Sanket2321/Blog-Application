package com.codingwithsanket.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codingwithsanket.blog.entities.Category;
import com.codingwithsanket.blog.entities.Post;
import com.codingwithsanket.blog.entities.User;
@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
	
      //custom finder method
      List<Post>findByUser(User user);
      List<Post>findByCategory(Category category);
       
}
