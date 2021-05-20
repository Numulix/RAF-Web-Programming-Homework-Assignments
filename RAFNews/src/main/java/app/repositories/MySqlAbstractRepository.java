package app.repositories;

import java.sql.*;
import java.util.Optional;

public class MySqlAbstractRepository {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DB_NAME = "raf_news";
    private static final String USERNAME = "jovan";
    private static final String PASSWORD = "sifra";

    public MySqlAbstractRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Connection newConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME,
                USERNAME,
                PASSWORD
        );
    }

    protected void closeStatement(Statement statement) {
        try {
            Optional.of(statement).get().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        try {
            Optional.of(resultSet).get().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection(Connection connection) {
        try {
            Optional.of(connection).get().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
