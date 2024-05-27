package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.ProductDTO;
import com.example.shopAppSpringBoot.dtos.ProductImageDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.Product;
import com.example.shopAppSpringBoot.models.ProductImage;
import com.example.shopAppSpringBoot.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
    Product getProductById(Long id) throws DataNotFoundException;
    Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;
    void deleteProduct(Long id) throws DataNotFoundException;
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    boolean existByName(String name);
    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException;
}
