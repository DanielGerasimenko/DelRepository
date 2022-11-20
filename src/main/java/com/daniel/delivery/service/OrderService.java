package com.daniel.delivery.service;

import com.daniel.delivery.dto.OrderCreateEditDto;
import com.daniel.delivery.dto.OrderReadDto;
import com.daniel.delivery.entity.Order;
import com.daniel.delivery.exception.OrderNotFoundException;
import com.daniel.delivery.map.OrderCreateEditMapper;
import com.daniel.delivery.map.OrderReadMapper;
import com.daniel.delivery.repository.OrderRepository;
import com.daniel.delivery.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCreateEditMapper orderCreateEditMapper;
    private final OrderReadMapper orderReadMapper;
    private final PersonRepository personRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderCreateEditMapper orderCreateEditMapper,
                        OrderReadMapper orderReadMapper,
                        PersonRepository personRepository) {
        this.orderRepository = orderRepository;
        this.orderCreateEditMapper = orderCreateEditMapper;
        this.orderReadMapper = orderReadMapper;
        this.personRepository = personRepository;
    }

    public List<OrderReadDto> getOrders(String email) {
        return personRepository.findByUsername(email)
                .map(person -> orderRepository.getAllByPersonId(person.getId()))
                .orElseThrow(() -> new RuntimeException())
                .stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public List<OrderReadDto> getOrdersAdmin() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public OrderReadDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

     @Transactional
    public OrderReadDto createOrder(OrderCreateEditDto orderDto, String email) {
        Order order = orderCreateEditMapper.map(orderDto);
        return personRepository.findByUsername(email)
                .map(person -> {
                    order.setPersonId(person.getId());
                    return orderRepository.save(order);
                })
                .map(orderReadMapper::map)
                .orElseThrow();
    }


    @Transactional
    public OrderReadDto updateOrderById(Long id, OrderCreateEditDto orderDto) {
        return orderRepository.findById(id)
                .map(order -> {
                    return orderCreateEditMapper.map(orderDto, order);
                })
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}