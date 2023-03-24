package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
