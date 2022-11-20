package com.daniel.delivery.controller;

import com.daniel.delivery.dto.OrderCreateEditDto;
import com.daniel.delivery.dto.OrderReadDto;
import com.daniel.delivery.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','COURIER')")
    public List<OrderReadDto> getOrders(Authentication auth) {
        String email = (String) ((DefaultOidcUser) auth.getPrincipal()).getClaims().get("email");
        return orderService.getOrders(email);
    }

    @GetMapping("Get orders if you Admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<OrderReadDto> getOrdersAdmin() {
        return orderService.getOrdersAdmin();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public OrderReadDto createOrder(@RequestBody OrderCreateEditDto orderCreateEditDto,
                                    Authentication auth) {
        String email = (String) ((DefaultOidcUser) auth.getPrincipal()).getClaims().get("email");
        return orderService.createOrder(orderCreateEditDto, email);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public OrderReadDto getOrder(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public OrderReadDto updateOrder(@PathVariable Long id, @RequestBody OrderCreateEditDto orderDto) {
        return orderService.updateOrderById(id, orderDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
    }
}