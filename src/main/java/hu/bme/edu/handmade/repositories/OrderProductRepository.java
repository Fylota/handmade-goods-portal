package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.OrderProducts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProducts, Long> {
}
