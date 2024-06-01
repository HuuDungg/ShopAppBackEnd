package com.example.shopAppSpringBoot.controllers;

import com.example.shopAppSpringBoot.dtos.ProductDTO;
import com.example.shopAppSpringBoot.dtos.ProductImageDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.Product;
import com.example.shopAppSpringBoot.models.ProductImage;
import com.example.shopAppSpringBoot.responses.ProductResponse;
import com.example.shopAppSpringBoot.responses.ProductlistResponse;
import com.example.shopAppSpringBoot.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private static final String UPLOAD_FOLDER = "/Users/huudung/Documents/work/ShopAnhHoang/Document/shopAppSpringBoot/src/main/resources/static";

    @GetMapping("/getAllProduct")
    public ResponseEntity<ProductlistResponse> getAllProduct(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
            ) {

        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("createdAt").descending()
        );

        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);

        int total = productPage.getTotalPages();

        List<ProductResponse> products = productPage.getContent();

        return ResponseEntity.ok(ProductlistResponse.builder().list(products).totalPages(total).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) throws DataNotFoundException {
        Product product = productService.getProductById(id);
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryId(product.getCategoryId().getId())
                .thumbnail(product.getThumbnail())
                .   description(product.getDescription())

                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return ResponseEntity.ok(productResponse);
    }
    @PostMapping(value = "")
    public ResponseEntity<String> insertCategory(@RequestBody ProductDTO productDTO) throws DataNotFoundException, IOException {
        System.out.println("da vao");

        Product product = productService.createProduct(productDTO);

        return ResponseEntity.ok("Insert successfully. File saved as: ");
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) throws DataNotFoundException{
        productService.updateProduct(id, productDTO);
        return ResponseEntity.ok("update successfully " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("delete successfully " + id);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable("imageName")String imageName){
            try {
                Path imagePath = Paths.get(UPLOAD_FOLDER + "/" + imageName);
                UrlResource urlResource = new UrlResource(imagePath.toUri());
                if( urlResource.exists()){
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(urlResource);
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
                }
            }catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is null");
            }


    }

    @PostMapping(value = "/saveFiles/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveFileImage(@RequestParam("files") MultipartFile multipartFile, @PathVariable("id") Long id) throws IOException, DataNotFoundException {

        if (multipartFile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is null");
        }
        if (multipartFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        String fileName = saveFile(multipartFile);

        ProductImage productImage =productService.createProductImage(id, ProductImageDTO.builder().imageUrl(fileName).build());

        productService.createProductImage(id, ProductImageDTO.builder().imageUrl(fileName).build());

        return ResponseEntity.ok().body("save file successfully " + fileName) ;
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please select a file to upload.");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File size is too big.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "File format not supported.");
        }

        Path uploadDir = Paths.get(UPLOAD_FOLDER);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String fileName = UUID.randomUUID() + StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
