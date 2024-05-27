package com.example.shopAppSpringBoot.reposotories;

import com.example.shopAppSpringBoot.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //kiem tra xxem ten nay ton tai khong
    boolean existsByName(String name);
    boolean existsById(Long id);
    //dung de phan trang
    Page<Product> findAll(Pageable pageable);

}
