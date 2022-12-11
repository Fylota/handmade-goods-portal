package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.model_assemblers.ProductModelAssembler;
import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.services.IProductService;
import hu.bme.edu.handmade.web.dto.ProductDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final ProductModelAssembler assembler;
    ProductController(IProductService productService, ProductModelAssembler assembler) {
        this.productService = productService;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public EntityModel<Product> getProduct(@PathVariable("id") Long id) {
        Product product = productService.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id:" + id + " is not found."));
        return assembler.toModel(product);
    }

    @GetMapping()
    public CollectionModel<EntityModel<Product>> getProducts() {
        List<Product> products = productService.findAllProducts();
        return assembler.toCollectionModel(products);
    }

    @GetMapping("/category/{id}")
    public CollectionModel<EntityModel<Product>> getProductsByCategory(@PathVariable("id") Long id) {
        List<Product> products = productService.findProductsByCategory(id);
        return assembler.toCollectionModel(products);
    }

    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
        EntityModel<Product> entityModel = assembler.toModel(productService.uploadNewProduct(productDto));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        Product updatedProduct = productService.findProductById(id)
                        .map(product -> productService.updateProduct(productDto))
                        .orElseGet(()->productService.uploadNewProduct(productDto));

        EntityModel<Product> entityModel = assembler.toModel(updatedProduct);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> deletedProduct = productService.findProductById(id);
        deletedProduct.ifPresent(productService::deleteProduct);
        return ResponseEntity.noContent().build();
    }
}
