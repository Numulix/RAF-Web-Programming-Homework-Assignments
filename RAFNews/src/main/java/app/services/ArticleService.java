package app.services;

import app.entities.Article;
import app.repositories.article.ArticleRepository;
import app.repositories.article.SqlArticleRepository;

public class ArticleService {

    private static final ArticleRepository articleRepository = new SqlArticleRepository();

    public static Article addArticle(Article article, String[] tags) {
        return articleRepository.addArticle(article, tags);
    }
}
