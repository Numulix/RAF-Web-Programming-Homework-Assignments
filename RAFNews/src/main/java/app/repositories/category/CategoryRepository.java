package app.repositories.category;

import app.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    public Category addCategory(Category category);
    public boolean deleteCategory(Integer id);
    public Category updateCategory(Category category);
    public List<Category> allCategories();
    public Category getSingleCategory(Integer id);
}
