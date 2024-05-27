package com.example.shopAppSpringBoot.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductlistResponse {
    private List<ProductResponse> list;
    private int totalPages;
}
