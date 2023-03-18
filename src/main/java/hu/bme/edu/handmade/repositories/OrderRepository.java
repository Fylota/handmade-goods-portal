package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Order;
import hu.bme.edu.handmade.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
