package hu.bme.edu.handmade.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "reviews")
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reviewer_name")
    private String reviewerName;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "rating")
    private Integer rating;
    @CreatedDate
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id.equals(review.id) && product.equals(review.product) && user.equals(review.user) && Objects.equals(content, review.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, user, content);
    }
}
