package hu.bme.edu.handmade.repositories;

import hu.bme.edu.handmade.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategory_Id(Long categoryId);
    Page<Product> findAllByCategory_Id(Pageable page, Long categoryId);
}
