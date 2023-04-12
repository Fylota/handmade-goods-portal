package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends CrudRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser_Id(Long id);
}
