package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.ProductDTO;
import com.example.shopAppSpringBoot.dtos.ProductImageDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.Category;
import com.example.shopAppSpringBoot.models.Product;
import com.example.shopAppSpringBoot.models.ProductImage;
import com.example.shopAppSpringBoot.reposotories.CategoryRepository;
import com.example.shopAppSpringBoot.reposotories.ProductImageRepository;
import com.example.shopAppSpringBoot.reposotories.ProductRepository;
import com.example.shopAppSpringBoot.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;


    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("category not found")
        );

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .categoryId(category)
               .build();

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("product not found")
        );
        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(
                () ->  new DataNotFoundException("not found product")
        );

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setThumbnail(productDTO.getThumbnail());
        product.setDescription(productDTO.getDescription());
        product.setCategoryId(categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("category not found")
        ));
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("category not found")
        );

        product.setCategoryId(category);

        return productRepository.save(product);

    }
    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException {
        // if product in productImage not present then break
        if (!productRepository.existsById(productId)){
            throw new DataNotFoundException("product not found");
        }

        //save productImage
        ProductImage productImage = ProductImage.builder()
                .imageUrl(productImageDTO.getImageUrl())
                .productId(productRepository.findById(productId).orElseThrow(
                        () -> new DataNotFoundException("product not found")
                ))
                .build();
        return productImageRepository.save(productImage);


    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(
                product -> {
                    ProductResponse productResponse = ProductResponse.builder()
                           .id(product.getId())
                           .name(product.getName())
                           .price(product.getPrice())
                           .thumbnail(product.getThumbnail())
                           .description(product.getDescription())
                            .categoryId(product.getCategoryId().getId())
                           .build();
                    productResponse.setCreatedAt(product.getCreatedAt());
                    productResponse.setUpdatedAt(product.getUpdatedAt());
                    return productResponse;
                }
        );
    }

    @Override
    public boolean existByName(String name) {
        return productRepository.existsByName(name);
    }


}
