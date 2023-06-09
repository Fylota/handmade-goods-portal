package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Category;
import hu.bme.edu.handmade.services.ICategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<Category> getCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/{category_name}")
    public Category getCategoryByName(@PathVariable("category_name") String name) {
        return categoryService.findCategoryByName(name);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping("")
    public Category addNewCategory(@RequestBody Category cat) {
        return categoryService.createCategory(cat);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PutMapping("/{category_id}")
    public Category updateCategory(@PathVariable("category_id") Long id, @RequestBody String newCatName) {
        return categoryService.updateCategory(id, newCatName);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @DeleteMapping("/{category_id}")
    public void deleteCategory(@PathVariable("category_id") Long id) {
        categoryService.deleteCategory(id);
    }
}
