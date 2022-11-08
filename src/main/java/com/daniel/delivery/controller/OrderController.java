package com.daniel.delivery.controller;

import com.daniel.delivery.dto.OrderCreateEditDto;
import com.daniel.delivery.dto.OrderReadDto;
import com.daniel.delivery.dto.PersonCreateEditDto;
import com.daniel.delivery.dto.PersonReadDto;
import com.daniel.delivery.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','COURIER')")
    public List<OrderReadDto> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public OrderReadDto createOrder(@RequestBody OrderCreateEditDto orderCreateEditDto) {
        return orderService.createOrder(orderCreateEditDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Optional<OrderReadDto> getOrder(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Optional<OrderReadDto> updateOrder(@PathVariable Long id, @RequestBody OrderCreateEditDto orderDto) {
        return orderService.updateOrderById(id, orderDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteOrder(@PathVariable("id") Long id) {
        if (!orderService.deleteOrderById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "Delete is OK";
    }

}