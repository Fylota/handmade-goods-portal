package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
}
