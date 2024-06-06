package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.CategoryDTO;
import com.example.shopAppSpringBoot.models.Category;
import com.example.shopAppSpringBoot.reposotories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    @Transactional
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category
                .builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () ->
                    new RuntimeException("category not found")

        );
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
    @Transactional
    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = Category
                .builder()
                .name(categoryDTO.getName())
                .build();

        Category category1 = categoryRepository.getById(id);

        category1.setName(category.getName());
        categoryRepository.save(category1);
        return category1;

    }
    @Transactional
    @Override
    public void deleteCategory(Long id) {
         categoryRepository.deleteById(id);
    }
}
