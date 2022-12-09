package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.models.Category;
import hu.bme.edu.handmade.repositories.CategoryRepository;
import hu.bme.edu.handmade.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

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
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }
}
