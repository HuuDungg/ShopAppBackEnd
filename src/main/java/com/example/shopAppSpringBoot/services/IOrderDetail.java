package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.OrderDetailDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.OrderDetail;

import java.util.List;

public interface IOrderDetail {
    List<OrderDetail> getAllOrderDetails();

    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO);

    OrderDetail getOrderDetailById(Long id) throws DataNotFoundException;

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO);

    void deleteOrderDetail(Long id);

    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}