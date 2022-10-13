package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
}
