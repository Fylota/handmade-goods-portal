package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.models.Category;
import hu.bme.edu.handmade.repositories.CategoryRepository;
import hu.bme.edu.handmade.services.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String categoryName) {
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> findAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, String name) {
        return categoryRepository.findById(id)
            .map(cat -> {
                cat.setName(name);
                return categoryRepository.save(cat);
            })
            .orElseGet(() -> {
                Category newCategory = new Category();
                newCategory.setId(id);
                newCategory.setName(name);
                return categoryRepository.save(newCategory);
            });
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }
}
