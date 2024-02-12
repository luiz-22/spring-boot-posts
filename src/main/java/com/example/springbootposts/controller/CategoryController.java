package com.example.springbootposts.controller;

import com.example.springbootposts.dto.CategoryDTO;
import com.example.springbootposts.exception.BadRequestException;
import com.example.springbootposts.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws BadRequestException {
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/all")
    public Set<CategoryDTO> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        try {
            CategoryDTO categoryDTO = categoryService.getCategory(id);
            return ResponseEntity.ok(categoryDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws BadRequestException {
        categoryService.updateCategory(categoryDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
