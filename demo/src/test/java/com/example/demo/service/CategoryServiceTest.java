package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.CreateCategoryRequest;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.entity.TransactionType;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void updateCategory_ShouldUpdateCategory() {

        Category category = new Category();
        category.setName("Category 1");
        category.setType(TransactionType.INCOME);
        category.setId(1);

        CreateCategoryRequest updatedCategoryRequest = new CreateCategoryRequest();
        updatedCategoryRequest.setName("Updated Category");
        updatedCategoryRequest.setType(category.getType().name());

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        categoryService.updateCategory(category.getId(), updatedCategoryRequest);

        assert (category.getName().equals("Updated Category"));

    }

    @Test
    public void updateCategory_ShouldThrowException_WhenCategoryNotFound() {

        CreateCategoryRequest updatedCategoryRequest = new CreateCategoryRequest();
        updatedCategoryRequest.setName("Updated Category");

        try {
            categoryService.updateCategory(999, updatedCategoryRequest);
        } catch (Exception e) {
            assert (e.getMessage().equals("Category not found with id: 999"));
        }

    }

    @Test
    public void createCategory_ShouldCreateCategory() {

        Category newCategory = new Category();
        newCategory.setName("New Category");
        newCategory.setType(TransactionType.INCOME);

        Mockito.when(categoryRepository.save(Mockito.any(Category.class)))
                .thenReturn(newCategory);

        CreateCategoryRequest categoryRequest = new CreateCategoryRequest();
        categoryRequest.setName("New Category");
        categoryRequest.setType(TransactionType.INCOME.name());

        Category category = categoryService.createCategory(categoryRequest);

        Mockito.verify(categoryRepository).save(Mockito.any(Category.class));

        assertEquals(
                newCategory.getName(),
                category.getName());

        assertEquals(
                newCategory.getType(),
                category.getType());

    }

    @Test
    public void deleteCategory_ShouldDeleteCategory() {

        Category category = new Category();
        category.setName("Category to Delete");
        category.setType(TransactionType.EXPENSE);

        category.setId(1);
        categoryService.deleteCategory(category.getId());

        // verify that the deleteById method was called with the correct id
        Mockito.verify(categoryRepository).deleteById(category.getId());
    }

    @Test
    public void getCategoryByName_ShouldReturnCategory() {

        Category category = new Category();
        category.setName("Category 1");
        category.setType(TransactionType.INCOME);

        Mockito.when(categoryRepository.findByName("Category 1")).thenReturn(category);

        Category result = categoryService.getCategoryByName("Category 1");

        assert (result.getName().equals("Category 1"));
        assert (result.getType() == TransactionType.INCOME);
    }

    @Test
    public void getCategoriesByType_ShouldReturnCategories() {

        Category category1 = new Category();
        category1.setName("Category 1");
        category1.setType(TransactionType.INCOME);

        Category category2 = new Category();
        category2.setName("Category 2");
        category2.setType(TransactionType.INCOME);

        Category category3 = new Category();
        category3.setName("Category 3");
        category3.setType(TransactionType.EXPENSE);

        Mockito.when(categoryRepository.findByType("INCOME")).thenReturn(List.of(category1, category2));

        List<Category> result = categoryService.getCategoriesByType("INCOME");

        assert (result.size() == 2);
        assert (result.get(0).getType() == TransactionType.INCOME);
        assert (result.get(1).getType() == TransactionType.INCOME);
    }

    @Test
    public void getAllCategories_ShouldReturnAllCategories() {

        Category category1 = new Category();
        category1.setName("Category 1");
        category1.setType(TransactionType.INCOME);

        Category category2 = new Category();
        category2.setName("Category 2");
        category2.setType(TransactionType.EXPENSE);

        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        List<Category> result = categoryService.getAllCategories();

        assert (result.size() == 2);
        assert (result.get(0).getName().equals("Category 1"));
        assert (result.get(0).getType() == TransactionType.INCOME);
        assert (result.get(1).getName().equals("Category 2"));
        assert (result.get(1).getType() == TransactionType.EXPENSE);
    }

}
