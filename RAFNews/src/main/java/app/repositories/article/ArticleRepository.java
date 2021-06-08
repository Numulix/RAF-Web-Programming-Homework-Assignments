package app.repositories.article;

import app.entities.Article;

import java.util.List;

public interface ArticleRepository {
    public Article addArticle(Article article, String[] tags);
    public Article editArticle(Article article, String[] tags);
    public boolean deleteArticle(Integer id);
    public Article getSingleArticle(Integer id);
    public List<Article> getArticlesPage(Integer page);
    public List<Article> getArticlesByCategoryPage(Integer categoryId, Integer page);
    public List<Article> getArticlesByTagPage(Integer tagId, Integer page);
    public Integer countArticles(Integer catId, Integer tagId);
    public List<Article> getMostRecentArticles();
    public List<Article> getMostReadMonthlyArticles();
}
