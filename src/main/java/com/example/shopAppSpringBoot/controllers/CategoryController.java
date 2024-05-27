package com.example.shopAppSpringBoot.controllers;

import com.example.shopAppSpringBoot.dtos.CategoryDTO;
import com.example.shopAppSpringBoot.models.Category;
import com.example.shopAppSpringBoot.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//dependency injection
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/category")
public class CategoryController {
    private final CategoryService categoryService;

    //create category
    @PostMapping("")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){
        if(result.hasErrors()){
            ResponseEntity.badRequest().body(result.getFieldError().toString());
        }
        categoryService.createCategory(categoryDTO );
        return ResponseEntity.ok("insert successfully " + categoryDTO);
    }
    //display all category
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam("page") int page, @RequestParam("limit") int limit){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }


    //method  update
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable("id") Long id,
            @RequestBody CategoryDTO categoryDTO
    ){
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("update succesfully");
    }
    //method delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("delete successfully " + id);
    }

}

