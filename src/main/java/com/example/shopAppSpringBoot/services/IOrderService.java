package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.OrderDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.Order;
import com.example.shopAppSpringBoot.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO);
    OrderResponse getOrderById(Long id) throws DataNotFoundException;
    Order updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
    List<OrderResponse> getAllOrders();
}
