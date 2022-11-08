package com.daniel.delivery.map;

import com.daniel.delivery.dto.OrderReadDto;
import com.daniel.delivery.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    @Override
    public OrderReadDto map(Order object) {
        return new OrderReadDto(
                object.getId(),
                object.getProduct(),
                object.getStatus(),
                object.getAddress(),
                object.getCreatedBy(),
                object.getCreatedAt()
        );
    }
}