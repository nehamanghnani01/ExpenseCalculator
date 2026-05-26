package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateCategoryRequest;
import com.example.demo.entity.Category;
import com.example.demo.entity.TransactionType;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CreateCategoryRequest categoryRequest) {

        Category category = new Category();

        category.setName(categoryRequest.getName());
        TransactionType type = TransactionType.valueOf(categoryRequest.getType().toUpperCase());
        category.setType(type);

        LocalDateTime now = LocalDateTime.now();

        category.setCreatedAt(now);
        category.setUpdatedAt(now);

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
