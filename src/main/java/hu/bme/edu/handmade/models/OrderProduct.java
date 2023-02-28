package hu.bme.edu.handmade.models;

import hu.bme.edu.handmade.models.primary_key.OrderProductId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_product")
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public OrderProduct() {}

    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
        this.id = new OrderProductId(order.getId(), product.getId());
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return order.equals(that.order) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
