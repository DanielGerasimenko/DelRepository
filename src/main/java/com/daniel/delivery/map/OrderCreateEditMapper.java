package com.daniel.delivery.map;

import com.daniel.delivery.dto.OrderCreateEditDto;
import com.daniel.delivery.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {
    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);

        return order;
    }

    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderCreateEditDto object, Order order) {
        order.setProduct(object.getProduct());
        order.setStatus(object.getStatus());
        order.setAddress(object.getAddress());
    }

}
