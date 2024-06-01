package com.example.shopAppSpringBoot.controllers;

import com.example.shopAppSpringBoot.dtos.OrderDetailDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.OrderDetail;
import com.example.shopAppSpringBoot.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
@RequiredArgsConstructor
public class OderDetailController {
    //injection dependencies
    private final OrderDetailService orderDetailService;
    //display all category
    @GetMapping("/getAllOderDetail")
    public ResponseEntity<List<OrderDetail>> getAllOderDdetail(@RequestParam("page") int page, @RequestParam("limit") int limit){

        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }
    //get details
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable("id") Long id) throws DataNotFoundException {
            return ResponseEntity.ok(orderDetailService.getOrderDetailById(id));
    }
    //create order detail
    @PostMapping("")
    public ResponseEntity<OrderDetail> insertOderDdetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailDTO));
    }
    //update order detail
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOderDdetail(@PathVariable("id") Long id,@RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(id, orderDetailDTO));
    }
    //delete order detail
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOderDdetail(@PathVariable("id") Long id){
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok("delete successfully " + id);
    }

}
