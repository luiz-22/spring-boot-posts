package com.example.springbootposts.service;

import com.example.springbootposts.dto.CategoryDTO;
import com.example.springbootposts.exception.BadRequestException;

import java.util.Set;

public interface ICategoryService {

    void createCategory(CategoryDTO categoryDTO) throws BadRequestException;
    Set<CategoryDTO> getCategories();
    CategoryDTO getCategory(Long id) throws BadRequestException;
    void updateCategory(CategoryDTO categoryDTO) throws BadRequestException;
    void deleteCategory(Long id);
}
