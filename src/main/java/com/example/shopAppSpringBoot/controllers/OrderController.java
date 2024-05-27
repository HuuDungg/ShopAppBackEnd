package com.example.shopAppSpringBoot.controllers;

import com.example.shopAppSpringBoot.dtos.CategoryDTO;
import com.example.shopAppSpringBoot.dtos.OrderDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.responses.OrderResponse;
import com.example.shopAppSpringBoot.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    //dependency injection
    private final OrderService oderService;

    //display all category
    @GetMapping("/getAllOrder")
    public ResponseEntity<List<OrderResponse>> getAllOrder(@RequestParam("page") int page, @RequestParam("limit") int limit){
        List<OrderResponse> orderResponses = oderService.getAllOrders();
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") Long id) throws DataNotFoundException {
        OrderResponse orderResponse = oderService.getOrderById(id);
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping("")
    public ResponseEntity<String> insertOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError().toString());
        }
        oderService.createOrder(orderDTO);
        return ResponseEntity.ok("insert successfully " + orderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError().toString());
        }
        oderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok("update successfully " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError().toString());
        }
        oderService.deleteOrder(id);
        return ResponseEntity.ok("delete successfully " + id);
    }
}
