package hu.bme.edu.handmade.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "newsletters")
public class Newsletter {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title")
    private String title;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "content")
    private byte[] content;
    @CreatedDate
    @Column(name = "creation_date")
    private Date creationDate;

    public Newsletter(String title, byte[] content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Newsletter that = (Newsletter) o;
        return id.equals(that.id) && title.equals(that.title) && Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}
