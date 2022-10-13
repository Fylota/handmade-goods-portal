package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
