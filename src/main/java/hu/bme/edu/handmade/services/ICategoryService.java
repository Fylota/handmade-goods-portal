package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(Category cat);
    List<Category> findAllCategories();
    Category updateCategory(Long id, String name);
    void deleteCategory(Long id);

    Category findCategoryByName(String name);
}
