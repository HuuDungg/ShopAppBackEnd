package com.example.shopAppSpringBoot.services;

import com.example.shopAppSpringBoot.dtos.OrderDetailDTO;
import com.example.shopAppSpringBoot.exceptions.DataNotFoundException;
import com.example.shopAppSpringBoot.models.OrderDetail;
import com.example.shopAppSpringBoot.reposotories.OrderDetailRepository;
import com.example.shopAppSpringBoot.reposotories.OrderRepository;
import com.example.shopAppSpringBoot.reposotories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetail{
    //injection dependency
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = OrderDetail.builder()
                .price(orderDetailDTO.getPrice())
                .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        orderDetail.setOrder(orderRepository.findById(orderDetailDTO.getOrderId()).get());
        orderDetail.setProduct(productRepository.findById(orderDetailDTO.getProductId()).get());
                return orderDetail;
    }

    @Override
    public OrderDetail getOrderDetailById(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("not found order detail")
        );
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).get();
            orderDetail.setPrice(orderDetailDTO.getPrice());
            orderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProduct());
            orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
            orderDetail.setColor(orderDetailDTO.getColor());
            orderDetail.setOrder(orderRepository.findById(orderDetailDTO.getOrderId()).get());
            orderDetail.setProduct(productRepository.findById(orderDetailDTO.getProductId()).get());
        return orderDetailRepository.save(orderDetail);

    }

    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);

    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
