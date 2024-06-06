package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.OrderDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.Order;
import com.example.shopAppSpringBoot.reposotories.OrderRepository;
import com.example.shopAppSpringBoot.reposotories.UserRepository;
import com.example.shopAppSpringBoot.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @Transactional
    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order order = Order
               .builder()
                .fullName(orderDTO.getFullName())
                .address(orderDTO.getAddress())
                .phoneNumber(orderDTO.getPhoneNumber())
                .email(orderDTO.getEmail())
                .note(orderDTO.getNote())
                .status(orderDTO.getStatus())
                .totalMoney(orderDTO.getTotalMoney())
                .shippingAddress(orderDTO.getShippingAddress())
                .paymentMethod(orderDTO.getPaymentMethod())
                .trackingNumber(UUID.randomUUID().toString())
               .build();
        order.setOrderDate(new Date());
        order.setUser(userRepository.findById(orderDTO.getUserId()).get());
       return orderRepository.save(order);
    }

    @Override
    public OrderResponse getOrderById(Long id) throws DataNotFoundException {
        Order order = orderRepository.findById(id).get();

        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .address(order.getAddress())
                .email(order.getEmail())
                .fullName(order.getFullName())
                .phoneNumber(order.getPhoneNumber())
                .note(order.getNote())
                .status(order.getStatus())
                .totalMoney(order.getTotalMoney())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .build();

        return orderResponse;
    }
    @Transactional
    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).get();
        order.setFullName(orderDTO.getFullName());
        order.setAddress(orderDTO.getAddress());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setEmail(orderDTO.getEmail());
        order.setNote(orderDTO.getNote());
        order.setStatus(orderDTO.getStatus());
        order.setTotalMoney(orderDTO.getTotalMoney());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        return orderRepository.save(order);
    }
    @Transactional
    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = orders.stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .userId(order.getUser().getId())
                        .fullName(order.getFullName())
                        .address(order.getAddress())
                        .email(order.getEmail())
                        .phoneNumber(order.getPhoneNumber())
                        .note(order.getNote())
                        .status(order.getStatus())
                        .totalMoney(order.getTotalMoney())
                        .shippingAddress(order.getShippingAddress())
                        .paymentMethod(order.getPaymentMethod())
                        .build())
                .toList();
        return orderResponses;
    }

}
