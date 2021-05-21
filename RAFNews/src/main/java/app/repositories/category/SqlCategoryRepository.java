package app.repositories.category;

import app.entities.Category;
import app.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {

    @Override
    public Category addCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO category (category_name, cat_description) VALUES (?, ?)",
                    generatedColumns
            );
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setString(2, category.getCategoryDescription());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                category.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return category;
    }

    @Override
    public void deleteCategory(Integer id) {

    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }
}
