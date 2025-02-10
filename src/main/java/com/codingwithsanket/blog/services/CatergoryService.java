package com.codingwithsanket.blog.services;

import java.util.List;

import com.codingwithsanket.blog.payloads.CategoryDto;

public interface CatergoryService {
	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

//delete
	 void deleteCategory(Integer categoryId);
	//get 
    CategoryDto getCategory(Integer categoryId);
    //get All
    List<CategoryDto> getCategories();
}
