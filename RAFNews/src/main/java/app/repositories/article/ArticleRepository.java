package app.repositories.article;

import app.entities.Article;

import java.util.List;

public interface ArticleRepository {
    public Article addArticle(Article article, String[] tags);
    public Article editArticle(Article article);
    public boolean deleteArticle(Integer id);
    public Article getSingleArticle(Integer id);
    public List<Article> getArticlesPage(Integer page);
}
