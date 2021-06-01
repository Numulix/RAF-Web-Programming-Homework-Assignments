package app.services;

import app.entities.Category;
import app.repositories.category.CategoryRepository;
import app.repositories.category.SqlCategoryRepository;

import java.util.List;

public class CategoryService {

    private static final CategoryRepository categoryRepository = new SqlCategoryRepository();

    public static Category addCategory(Category category) {
        return categoryRepository.addCategory(category);
    }
    public static boolean deleteCategory(Integer id) {
        return categoryRepository.deleteCategory(id);
    }
    public static Category updateCategory(Category category) {
        return categoryRepository.updateCategory(category);
    }
    public static List<Category> allCategories() {
        return categoryRepository.allCategories();
    }
    public static Category getSingleCategory(Integer id) {
        return categoryRepository.getSingleCategory(id);
    }
}
