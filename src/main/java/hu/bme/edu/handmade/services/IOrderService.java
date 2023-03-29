package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Order;
import hu.bme.edu.handmade.web.dto.order.OrderCreateDto;
import hu.bme.edu.handmade.web.dto.order.OrderItemDto;

import java.util.List;

public interface IOrderService {
    OrderItemDto createNewOrder(OrderCreateDto order);

    List<OrderItemDto> findAllOrders();

    List<OrderItemDto> getOrdersByUser(Long userId);

    OrderItemDto getOrderById(Long orderId);
    Order updateOrder(Order order);
}
