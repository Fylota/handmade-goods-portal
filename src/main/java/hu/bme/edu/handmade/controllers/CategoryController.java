package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Category;
import hu.bme.edu.handmade.services.ICategoryService;
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


}
