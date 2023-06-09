package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    List<User> findUsersBySubscribedToNewsletterTrue();
    boolean existsByEmail(String email);
}
