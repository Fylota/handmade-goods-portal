package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(String categoryName);
    List<Category> findAllCategories();
    void updateCategory(Category category);
    void deleteCategory(Category category);

    Category findCategoryByName(String name);
}
