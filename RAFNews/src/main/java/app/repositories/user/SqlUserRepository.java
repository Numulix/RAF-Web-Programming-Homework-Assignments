package app.repositories.user;

import app.entities.User;
import app.repositories.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlUserRepository extends MySqlAbstractRepository implements UserRepository {

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?"
            );
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                closeResultSet(resultSet);
                closeStatement(preparedStatement);
                closeConnection(connection);
                return null;
            }

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO user (email, name, surname, password) VALUES (?, ?, ?, ?)",
                    generatedColumns
            );
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, DigestUtils.sha256Hex(user.getPassword()));
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?"
            );
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setStatus(resultSet.getString("status"));
                user.setRole(resultSet.getString("role"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public List<User> listAllUsers() {
        return null;
    }
}
