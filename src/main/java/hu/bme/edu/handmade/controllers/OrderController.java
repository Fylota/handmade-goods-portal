package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.services.IOrderService;
import hu.bme.edu.handmade.web.dto.order.OrderCreateDto;
import hu.bme.edu.handmade.web.dto.order.OrderItemDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<OrderItemDto> getOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/user")
    public List<OrderItemDto> getOrdersByUserId(@RequestParam Long id) {
        return orderService.getOrdersByUser(id);
    }

    @PostMapping()
    public OrderItemDto addOrder(@RequestBody OrderCreateDto order) {
        return orderService.createNewOrder(order);
    }

    @GetMapping("/{id}")
    public OrderItemDto getOrderById(@PathVariable("id") Long orderId) {
        return orderService.getOrderById(orderId);
    }

}
