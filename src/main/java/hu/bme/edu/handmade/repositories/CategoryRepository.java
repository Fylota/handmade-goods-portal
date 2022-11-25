package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findCategoryByName(String name);
}
