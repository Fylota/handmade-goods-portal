package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.web.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product uploadNewProduct(ProductDto productDto);

    Product updateProduct(ProductDto productDto, Long productId);

    void deleteProduct(Long productId);

    Optional<Product> findProductById(Long productId);

    List<Product> findAllProducts();

    List<Product> findProductsByCategory(Long categoryId);

    Page<Product> findAll(Pageable paging);
}
