package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findAllByProductId(Long id);
}
