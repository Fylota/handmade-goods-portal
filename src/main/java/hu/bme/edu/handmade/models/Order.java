package hu.bme.edu.handmade.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "creation_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    @Column(name = "status")
    private String status;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "shipping_method")
    private String shippingMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "order"
            //cascade = CascadeType.ALL,
            //orphanRemoval = true
    )
    private List<OrderProduct> products = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        OrderProduct orderProduct = new OrderProduct(this, product);
        orderProduct.setQuantity(quantity);
        products.add(orderProduct);
    }

    public void removeProduct(Product product) {
        for (Iterator<OrderProduct> iterator = products.iterator();
             iterator.hasNext(); ) {
            OrderProduct orderProduct = iterator.next();

            if (orderProduct.getOrder().equals(this) &&
                    orderProduct.getProduct().equals(product)) {
                iterator.remove();
                orderProduct.setOrder(null);
                orderProduct.setProduct(null);
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) && Objects.equals(creationDate, order.creationDate) && paymentMethod.equals(order.paymentMethod) && shippingMethod.equals(order.shippingMethod) && user.equals(order.user) && products.equals(order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentMethod, shippingMethod, user, products);
    }
}
