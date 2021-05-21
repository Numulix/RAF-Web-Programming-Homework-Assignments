package app.repositories.category;

import app.entities.Category;

import java.util.Optional;

public interface CategoryRepository {
    public Category addCategory(Category category);
    public void deleteCategory(Integer id);
    public Category updateCategory(Category category);
}
