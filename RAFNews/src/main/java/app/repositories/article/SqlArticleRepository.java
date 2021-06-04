package app.repositories.article;

import app.entities.Article;
import app.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository{

    @Override
    public Article addArticle(Article article, String[] tags) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO article (title, content, author_id, category_id)" +
                            " VALUES (?, ?, ?, ?)",
                    generatedColumns
            );
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setInt(3, article.getAuthorId());
            preparedStatement.setInt(4, article.getCategoryId());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Integer articleId = resultSet.getInt(1);

//            closeResultSet(resultSet);
//            closeStatement(preparedStatement);

            for (String tag: tags) {
                preparedStatement = connection.prepareStatement(
                        "SELECT * FROM tag WHERE tag_name = ?"
                );
                preparedStatement.setString(1, tag);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
//                    closeStatement(preparedStatement);
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO tag_news_article (tag_id, article_id) VALUES ((SELECT id FROM tag WHERE tag_name = ?), ?)"
                    );
                    preparedStatement.setString(1, tag);
                    preparedStatement.setInt(2, articleId);
                    preparedStatement.executeUpdate();
//                    closeStatement(preparedStatement);
                } else {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO tag (tag_name) VALUES (?)"
                    );
                    preparedStatement.setString(1, tag);
                    preparedStatement.executeUpdate();
//                    closeStatement(preparedStatement);
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO tag_news_article (tag_id, article_id) VALUES ((SELECT id FROM tag WHERE tag_name = ?), ?)"
                    );
                    preparedStatement.setString(1, tag);
                    preparedStatement.setInt(2, articleId);
                    preparedStatement.executeUpdate();
//                    closeStatement(preparedStatement);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return article;
    }

    @Override
    public Article editArticle(Article article) {
        return null;
    }

    @Override
    public boolean deleteArticle(Integer id) {
        return false;
    }

    @Override
    public Article getSingleArticle(Integer id) {
        return null;
    }

    @Override
    public List<Article> getArticlesPage(Integer page) {
        return null;
    }
}
