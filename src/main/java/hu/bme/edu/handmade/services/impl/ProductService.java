package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.ProductMapper;
import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.repositories.CategoryRepository;
import hu.bme.edu.handmade.repositories.ProductRepository;
import hu.bme.edu.handmade.services.IProductService;
import hu.bme.edu.handmade.web.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product uploadNewProduct(ProductDto productDto) {
        Product product = ProductMapper.INSTANCE.toProduct(productDto);
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()).orElseThrow());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDto productDto, Long productId) {
        Product foundProduct = productRepository.findById(productId).orElseThrow();
        ProductMapper.INSTANCE.updateProductFromDto(productDto, foundProduct);
        foundProduct.setCategory(categoryRepository.findById(productDto.getCategoryId()).orElseThrow());
        return productRepository.save(foundProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByCategory(Long categoryId) {
        return productRepository.findProductsByCategory_Id(categoryId);
    }

    @Override
    public Page<Product> findPagesByCategory(Pageable paging, Long categoryId) {
        return productRepository.findAllByCategory_Id(paging, categoryId);
    }

    @Override
    public Page<Product> findAll(Pageable paging) {
        return productRepository.findAll(paging);
    }
}
