package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.CartProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
    List<CartProduct> findCartProductsByUserId(Long userId);
}
