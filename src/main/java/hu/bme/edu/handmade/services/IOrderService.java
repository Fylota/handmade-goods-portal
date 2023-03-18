package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Order;
import hu.bme.edu.handmade.web.dto.OrderCreateDto;
import hu.bme.edu.handmade.web.dto.OrderItemDto;

import java.util.List;

public interface IOrderService {
    Order createNewOrder(OrderCreateDto order);

    List<OrderItemDto> findAllOrders();

    List<Order> getOrdersByUser(Long userId);
    Order updateOrder(Order order);
}
