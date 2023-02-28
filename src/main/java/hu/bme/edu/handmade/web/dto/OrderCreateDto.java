package hu.bme.edu.handmade.web.dto;

import java.util.List;

public class OrderCreateDto {
    String userId;
    List<OrderProductDto> items;
    String paymentMethod;
    String shippingMethod;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderProductDto> getItems() {
        return items;
    }

    public void setItems(List<OrderProductDto> items) {
        this.items = items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
