package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.models.Order;
import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.repositories.OrderRepository;
import hu.bme.edu.handmade.services.IOrderService;
import hu.bme.edu.handmade.web.dto.OrderCreateDto;
import hu.bme.edu.handmade.web.dto.OrderProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public Order createNewOrder(OrderCreateDto order) {
        Order newOrder = new Order();
        newOrder.setStatus("CREATED");
        newOrder.setUser(userService.getUserByID(Long.parseLong(order.getUserId())).get());
        newOrder.setPaymentMethod(order.getPaymentMethod());
        newOrder.setShippingMethod(order.getShippingMethod());

        List<OrderProductDto> items = order.getItems();
        items.forEach(item -> {
            Product product = productService.findProductById(item.getProductId()).get();
            newOrder.addProduct(product, item.getQuantity());
        });

        return orderRepository.save(newOrder);
    }

    @Override
    public List<Order> findAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }
}
