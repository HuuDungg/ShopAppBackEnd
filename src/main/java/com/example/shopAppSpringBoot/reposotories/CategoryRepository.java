package com.example.shopAppSpringBoot.reposotories;

import com.example.shopAppSpringBoot.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
