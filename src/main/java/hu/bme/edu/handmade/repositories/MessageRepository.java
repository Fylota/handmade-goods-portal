package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
