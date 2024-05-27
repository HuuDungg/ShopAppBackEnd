package com.example.shopAppSpringBoot.reposotories;

import com.example.shopAppSpringBoot.models.Product;
import com.example.shopAppSpringBoot.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    boolean existsById(Long id);
    List<ProductImage> findByProductId(Product product);
}
