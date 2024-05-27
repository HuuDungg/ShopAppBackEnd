package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.CategoryDTO;
import com.example.shopAppSpringBoot.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategory();

    Category updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
