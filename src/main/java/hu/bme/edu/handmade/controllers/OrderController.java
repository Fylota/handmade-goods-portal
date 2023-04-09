package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.services.IOrderService;
import hu.bme.edu.handmade.web.dto.order.OrderCreateDto;
import hu.bme.edu.handmade.web.dto.order.OrderItemDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping()
    public List<OrderItemDto> getOrders() {
        return orderService.findAllOrders();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public List<OrderItemDto> getOrdersByUserId(@RequestParam Long id) {
        return orderService.getOrdersByUser(id);
    }


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping()
    public OrderItemDto addOrder(@RequestBody OrderCreateDto order) {
        return orderService.createNewOrder(order);
    }

    @GetMapping("/{id}")
    public OrderItemDto getOrderById(@PathVariable("id") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public OrderItemDto updateOrderStatus(@PathVariable("id") Long orderId, @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

}
