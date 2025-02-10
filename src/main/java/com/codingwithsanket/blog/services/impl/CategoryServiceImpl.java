package com.codingwithsanket.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingwithsanket.blog.entities.Category;
import com.codingwithsanket.blog.exceptions.ResourceNotFoundException;
import com.codingwithsanket.blog.payloads.CategoryDto;
import com.codingwithsanket.blog.repositories.CategoryRepo;
import com.codingwithsanket.blog.services.CatergoryService;
@Service
public class CategoryServiceImpl implements CatergoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	// category ko categorydto me aur dto ko category me
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedcat = this.categoryRepo.save(cat);
		// TODO Auto-generated method stub
		return this.modelMapper.map(addedcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcat = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedcat, CategoryDto.class);
		// TODO Auto-generated method stub

	}

	@Override

	public void deleteCategory(Integer categoryId) {
		// Step 1: Find the category in the database
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		// Step 2: Delete the found category
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// Step 1: Find the category in the database
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		// Step 2: Convert Entity to DTO and return
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		// Step 1: Get all categories from the repository
		List<Category> categories = this.categoryRepo.findAll();

		// Step 2: Convert each Category to CategoryDto using ModelMapper
		List<CategoryDto> categoryDtos = categories.stream()
				.map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

		// Step 3: Return the list of CategoryDto
		return categoryDtos;
	}

}
