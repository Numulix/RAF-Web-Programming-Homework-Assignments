package app.repositories.comment;

import app.entities.Comment;
import app.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlCommentRepository extends MySqlAbstractRepository implements CommentRepository{
    @Override
    public Comment addComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO comment (article_id, author, content, publish_date) " +
                            "VALUES (?,?,?,NOW())"
            );
            preparedStatement.setInt(1, comment.getPostId());
            preparedStatement.setString(2, comment.getAuthor());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

        return comment;
    }

    @Override
    public List<Comment> getCommentsForArticle(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Comment> comments = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM comment WHERE article_id = ? ORDER BY publish_date DESC"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment toAdd = new Comment();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setAuthor(resultSet.getString("author"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setPublishDate(resultSet.getDate("publish_date"));

                comments.add(toAdd);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return comments;
    }
}
