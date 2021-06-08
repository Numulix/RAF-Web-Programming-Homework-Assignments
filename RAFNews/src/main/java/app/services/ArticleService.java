package app.services;

import app.entities.Article;
import app.repositories.article.ArticleRepository;
import app.repositories.article.SqlArticleRepository;

import java.util.List;

public class ArticleService {

    private static final ArticleRepository articleRepository = new SqlArticleRepository();

    public static Article addArticle(Article article, String[] tags) {
        return articleRepository.addArticle(article, tags);
    }

    public static Article getSingleArticle(Integer id) {
        return articleRepository.getSingleArticle(id);
    }

    public static boolean deleteArticle(Integer id) {
        return articleRepository.deleteArticle(id);
    }

    public static Article editArticle(Article article, String[] tags) {
        return articleRepository.editArticle(article, tags);
    }

    public static List<Article> getArticlesPage(Integer page) {
        return articleRepository.getArticlesPage(page);
    }

    public static Integer getCount(Integer catId, Integer tagId) {
        return articleRepository.countArticles(catId, tagId);
    }

    public static List<Article> getArticlesByCategoryPage(Integer catId, Integer page) {
        return articleRepository.getArticlesByCategoryPage(catId, page);
    }

    public static List<Article> getArticlesByTagPage(Integer tagId, Integer page) {
        return articleRepository.getArticlesByTagPage(tagId, page);
    }

    public static List<Article> getMostRecentArticles() {
        return articleRepository.getMostRecentArticles();
    }

    public static List<Article> getMostReadArticlesMonthly() {
        return articleRepository.getMostReadMonthlyArticles();
    }
}
