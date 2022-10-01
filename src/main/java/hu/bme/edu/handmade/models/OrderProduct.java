package hu.bme.edu.handmade.models;

import javax.persistence.*;

@Entity
@Table(name = "order_products", schema = "public", catalog = "postgres")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public OrderProduct() { }

    @Column(nullable = false)
    private Integer quantity;

    @Transient
    public Product getProduct() {
        return this.getProduct();
    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
