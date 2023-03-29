package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.services.IProductService;
import hu.bme.edu.handmade.services.IReviewService;
import hu.bme.edu.handmade.web.dto.ProductDto;
import hu.bme.edu.handmade.web.dto.ReviewDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final IReviewService reviewService;
    ProductController(IProductService productService, IReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id:" + id + " is not found."));
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/categories")
    public List<Product> getProductsByCategory(@RequestParam Long id) {
        return  productService.findProductsByCategory(id);
    }

    @PostMapping()
    public Product addProduct(@RequestBody ProductDto productDto) {
        return productService.uploadNewProduct(productDto);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        return productService.findProductById(id)
                        .map(product -> productService.updateProduct(productDto, id))
                        .orElseGet(()->productService.uploadNewProduct(productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** Product reviews */

    @GetMapping("/{productId}/comments")
    public List<ReviewDto> getReviews(@PathVariable("productId") Long productId) {
        return reviewService.getReviews(productId);
    }

    @PostMapping("/{productId}/comments")
    public ReviewDto addReview(@PathVariable("productId") Long productId, @RequestBody ReviewDto review) {
        return reviewService.addReview(productId, review);
    }
}
