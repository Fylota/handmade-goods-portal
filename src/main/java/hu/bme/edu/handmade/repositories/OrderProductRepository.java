package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
}
