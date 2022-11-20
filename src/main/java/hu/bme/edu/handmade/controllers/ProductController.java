package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.services.IProductService;
import hu.bme.edu.handmade.web.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping("")
    public List<Product> getProducts() {
        return productService.findAllProducts();
    }
    @PostMapping("")
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.uploadNewProduct(productDto);
    }
    @PutMapping("/{id}")
    public String updateProduct(@RequestBody ProductDto productDto) {
        productService.updateProduct(productDto);
        return productDto.getId();
    }
    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable("id") long id) {
        Optional<Product> deletedProduct = productService.findProductById(id);
        deletedProduct.ifPresent(p -> productService.deleteProduct(p));
        return deletedProduct.orElseGet(Product::new);
    }
}
