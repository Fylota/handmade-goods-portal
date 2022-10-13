package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
