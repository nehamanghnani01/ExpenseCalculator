package com.example.demo.controller;

import com.example.demo.dto.CreateCategoryRequest;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@Valid @RequestBody CreateCategoryRequest category) {
        return categoryService.createCategory(category);
    }

    @GetMapping()
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }

    @PatchMapping("/{id}")
    public Category updateCategory(@PathVariable Integer id, @Valid @RequestBody CreateCategoryRequest category) {
        return categoryService.updateCategory(id, category);
    }
}
