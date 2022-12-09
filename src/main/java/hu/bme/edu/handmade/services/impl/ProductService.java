package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.ProductMapper;
import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.repositories.CategoryRepository;
import hu.bme.edu.handmade.repositories.ProductRepository;
import hu.bme.edu.handmade.services.IProductService;
import hu.bme.edu.handmade.web.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private  CategoryRepository categoryRepository;

    @Override
    public Product uploadNewProduct(ProductDto productDto) {
        Product product = ProductMapper.INSTANCE.toProduct(productDto);
        product.setCategory(categoryRepository.findById(Long.parseLong(productDto.getCategory())).orElse(null));
        return productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        Optional<Product> foundProduct = productRepository.findById(Long.parseLong(productDto.getId()));
        foundProduct.ifPresent(p -> productRepository.save(p));
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByCategory(Long categoryId) {
        return productRepository.findProductsByCategory_Id(categoryId);
    }
}
