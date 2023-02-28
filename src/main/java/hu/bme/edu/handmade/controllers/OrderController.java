package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Order;
import hu.bme.edu.handmade.services.IOrderService;
import hu.bme.edu.handmade.web.dto.OrderCreateDto;
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
    public List<Order> getOrders() {
        return orderService.findAllOrders();
    }

    @PostMapping()
    public Order addOrder(@RequestBody OrderCreateDto order) {
        return orderService.createNewOrder(order);
    }

}
