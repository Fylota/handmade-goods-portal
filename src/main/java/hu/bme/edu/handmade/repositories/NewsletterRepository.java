package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Newsletter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsletterRepository extends CrudRepository<Newsletter, Long> {
}
