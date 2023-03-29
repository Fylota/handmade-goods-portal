package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.OrderItemMapper;
import hu.bme.edu.handmade.models.Order;
import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.repositories.OrderRepository;
import hu.bme.edu.handmade.services.IOrderService;
import hu.bme.edu.handmade.web.dto.order.OrderCreateDto;
import hu.bme.edu.handmade.web.dto.order.OrderItemDto;
import hu.bme.edu.handmade.web.dto.order.OrderProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public OrderItemDto createNewOrder(OrderCreateDto order) {
        Order newOrder = new Order();
        newOrder.setStatus("CREATED");
        newOrder.setUser(userService.getUserByID(Long.parseLong(order.getUserId())).orElseThrow());
        newOrder.setPaymentMethod(order.getPaymentMethod());
        newOrder.setShippingMethod(order.getShippingMethod());

        List<OrderProductDto> items = order.getItems();
        items.forEach(item -> {
            Optional<Product> product = productService.findProductById(item.getProductId());
            product.ifPresent( p -> newOrder.addProduct(p, item.getQuantity()));
        });

        return OrderItemMapper.INSTANCE.orderToOrderItemDto(orderRepository.save(newOrder));
    }

    @Override
    public List<OrderItemDto> findAllOrders() {
        List<Order> orders = (List<Order>) orderRepository.findAll();
        return orders.stream().map(OrderItemMapper.INSTANCE::orderToOrderItemDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderItemDto> getOrdersByUser(Long userId) {
        User user = userService.getUserByID(userId).orElse(null);
        List<Order> orders = orderRepository.findAllByUser(user);
        return orders.stream().map(OrderItemMapper.INSTANCE::orderToOrderItemDto).collect(Collectors.toList());
    }

    @Override
    public OrderItemDto updateOrderStatus(Long id, String newStatus) {
        Order foundOrder = orderRepository.findById(id).orElseThrow();
        foundOrder.setStatus(newStatus);
        return OrderItemMapper.INSTANCE.orderToOrderItemDto(foundOrder);
    }

    @Override
    public OrderItemDto getOrderById(Long orderId) {
        return OrderItemMapper.INSTANCE.orderToOrderItemDto(orderRepository.findById(orderId).orElseThrow());
    }
}
