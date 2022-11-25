package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.web.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product uploadNewProduct(ProductDto productDto);

    void updateProduct(ProductDto productDto);

    void deleteProduct(Product product);

    Optional<Product> findProductById(Long productId);

    List<Product> findAllProducts();

    List<Product> findProductsByCategory(Long categoryId);
}
