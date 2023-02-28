package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Order;
import hu.bme.edu.handmade.web.dto.OrderCreateDto;

import java.util.List;

public interface IOrderService {
    Order createNewOrder(OrderCreateDto order);

    List<Order> findAllOrders();
    Order updateOrder(Order order);
}
