package hu.bme.edu.handmade.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "zip")
    private String zip;

    // Street, building, floor etc.
    @Column(name = "address_line")
    private String addressLine;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id.equals(address.id) && Objects.equals(city, address.city) &&
                Objects.equals(country, address.country) && Objects.equals(zip, address.zip) &&
                Objects.equals(addressLine, address.addressLine) && Objects.equals(user, address.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, country, zip, addressLine, user);
    }
}
