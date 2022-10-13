package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Long> {
}
