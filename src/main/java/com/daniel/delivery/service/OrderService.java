package com.daniel.delivery.service;

import com.daniel.delivery.dto.OrderCreateEditDto;
import com.daniel.delivery.dto.OrderReadDto;
import com.daniel.delivery.exception.OrderNotFoundException;
import com.daniel.delivery.map.OrderCreateEditMapper;
import com.daniel.delivery.map.OrderReadMapper;
import com.daniel.delivery.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCreateEditMapper orderCreateEditMapper;
    private final OrderReadMapper orderReadMapper;

    public OrderService(OrderRepository orderRepository, OrderCreateEditMapper orderCreateEditMapper, OrderReadMapper orderReadMapper) {
        this.orderRepository = orderRepository;
        this.orderCreateEditMapper = orderCreateEditMapper;
        this.orderReadMapper = orderReadMapper;
    }


    public List<OrderReadDto> getOrders() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public Optional<OrderReadDto> getOrderById(Long id) throws OrderNotFoundException {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto createOrder(OrderCreateEditDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> updateOrderById(Long id, OrderCreateEditDto orderDto) throws OrderNotFoundException {
        return orderRepository.findById(id)
                .map(order -> {
                    return orderCreateEditMapper.map(orderDto, order);
                })
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public boolean deleteOrderById(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.delete(order);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }


}