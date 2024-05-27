package com.example.shopAppSpringBoot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
        @NotBlank(message = "not be empty")
        @Size(min = 3, max = 40, message = "in range 3-40 character")
        private String name;
        private Float price;
        private String thumbnail;
        private String description;

        @JsonProperty("category_id")
        private Long categoryId;

        }
