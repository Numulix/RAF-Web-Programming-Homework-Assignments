package app.services;

import app.entities.Category;
import app.repositories.category.CategoryRepository;
import app.repositories.category.SqlCategoryRepository;

public class CategoryService {

    private static final CategoryRepository categoryRepository = new SqlCategoryRepository();

    public static Category addCategory(Category category) {
        return categoryRepository.addCategory(category);
    }

}
