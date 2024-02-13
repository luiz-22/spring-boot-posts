package com.example.springbootposts.service.impl;

import com.example.springbootposts.dto.CategoryDTO;
import com.example.springbootposts.entity.Category;
import com.example.springbootposts.exception.BadRequestException;
import com.example.springbootposts.repository.ICategoryRepository;
import com.example.springbootposts.service.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper mapper;


    @Override
    public void createCategory(CategoryDTO categoryDTO) throws BadRequestException {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategory.isPresent()) {
            throw new BadRequestException("Category already exists");
        }

        Category newCategory = mapper.convertValue(categoryDTO, Category.class);
        categoryRepository.save(newCategory);
    }


    @Override
    public Set<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        Set<CategoryDTO> categoriesDTO = new HashSet<>();

        for (Category category : categories) {
            categoriesDTO.add(mapper.convertValue(category, CategoryDTO.class));
        }

        return categoriesDTO;
    }

    @Override
    public CategoryDTO getCategory(Long id) throws BadRequestException {
        Optional<Category> found = categoryRepository.findById(id);
        if (found.isPresent())
            return mapper.convertValue(found, CategoryDTO.class);
        else
            throw new BadRequestException("Category Not Exist");
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) throws BadRequestException {
        Category updatedCategory = mapper.convertValue(categoryDTO, Category.class);
        categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
