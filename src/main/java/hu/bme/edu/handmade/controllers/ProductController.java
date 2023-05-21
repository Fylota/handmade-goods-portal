package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.services.IProductService;
import hu.bme.edu.handmade.services.IReviewService;
import hu.bme.edu.handmade.web.dto.ProductDto;
import hu.bme.edu.handmade.web.dto.ReviewDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final IReviewService reviewService;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
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
    public ResponseEntity<Map<String, Object>> getProducts(@RequestParam(required = false) Long categoryId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "3") int size,
                                                           @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            List<Sort.Order> orders = new ArrayList<>();
            Page<Product> pageProds;

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            Pageable paging = PageRequest.of(page, size, Sort.by(orders));
            if (categoryId == null) {
                pageProds = productService.findAll(paging);
            } else {
                pageProds = productService.findPagesByCategory(paging, categoryId);
            }

            List<Product> products = pageProds.getContent();

            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            response.put("currentPage", pageProds.getNumber());
            response.put("totalItems", pageProds.getTotalElements());
            response.put("totalPages", pageProds.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/categories")
    public List<Product> getProductsByCategory(@RequestParam Long id) {
        return productService.findProductsByCategory(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping()
    public Product addProduct(@RequestBody ProductDto productDto) {
        return productService.uploadNewProduct(productDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        return productService.findProductById(id)
                        .map(product -> productService.updateProduct(productDto, id))
                        .orElseGet(()->productService.uploadNewProduct(productDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
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

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping("/{productId}/comments")
    public ReviewDto addReview(@PathVariable("productId") Long productId, @RequestBody ReviewDto review) {
        return reviewService.addReview(productId, review);
    }
}
