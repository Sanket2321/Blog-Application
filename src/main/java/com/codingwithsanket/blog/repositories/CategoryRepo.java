package com.codingwithsanket.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codingwithsanket.blog.entities.Category;
@Repository
public  interface CategoryRepo extends JpaRepository<Category,Integer> {

}
