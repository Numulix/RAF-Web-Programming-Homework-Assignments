package app.controllers;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import app.auth.AuthService;
import app.entities.Article;
import app.entities.response.JSONResponseObject;
import app.services.ArticleService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class ArticleController {

    private static Validator<Article> articleValidator = ValidatorBuilder.<Article> of()
            .constraint(Article::getTitle, "title", c -> c.notNull()
                .notBlank()
                .notEmpty())
            .constraint(Article::getContent, "title", c -> c.notNull()
                .notEmpty()
                .notBlank())
            .constraint(Article::getCategoryId, "category", c -> c.notNull()
                .greaterThanOrEqual(1))
            .constraint(Article::getAuthorId, "author", c -> c.notNull()
                .greaterThanOrEqual(1))
            .build();

    public static Route addArticle = (Request req, Response res) -> {
        String header = req.headers("Authorization");
        if (header == null || !AuthService.isAuthorized(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        Article article = new Article();
        JsonObject o = JsonParser.parseString(req.body()).getAsJsonObject();
        article.setTitle(o.get("title").getAsString());
        article.setContent(o.get("content").getAsString());
        article.setAuthorId(o.get("authorId").getAsInt());
        article.setCategoryId(o.get("categoryId").getAsInt());

        ConstraintViolations cv = articleValidator.validate(article);

        if (!cv.isValid()) {
            StringBuilder errors = new StringBuilder();
            cv.forEach(x -> errors.append(x.message()+"\n"));
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", errors.toString())
            );
        }

        String[] tags = o.get("tags").getAsString().split(",");

        article = ArticleService.addArticle(article, tags);

        return new Gson().toJson(
                new JSONResponseObject(
                        "SUCCESS",
                        new Gson().toJsonTree(article)
                )
        );
    };

    public static Route getSingleArticle = (Request req, Response res) -> {
        try {
            Article article = ArticleService.getSingleArticle(Integer.parseInt(req.params(":id")));

            if (article == null) {
                res.status(404);
                return new Gson().toJson(
                        new JSONResponseObject("ERROR", "No article found with given id")
                );
            }

            return new Gson().toJson(
                    new JSONResponseObject(
                            "SUCCESS",
                            new Gson().toJsonTree(article)
                    )
            );
        } catch (NumberFormatException e) {
            return new Gson().toJson(
                    new JSONResponseObject(
                            "ERROR",
                            "Invalid parameter"
                    )
            );
        }
    };

    public static Route deleteArticle = (Request req, Response res) -> {
        String header = req.headers("Authorization");
        if (header == null || !AuthService.isAuthorized(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        try {
            boolean value = ArticleService.deleteArticle(Integer.parseInt(req.params(":id")));

            if (value) {
                return new Gson().toJson(
                        new JSONResponseObject(
                                "SUCCESS"
                        )
                );
            }

            return new Gson().toJson(
                    new JSONResponseObject(
                            "ERROR",
                            "No article found with given id"
                    )
            );
        } catch (NumberFormatException e) {
            return new Gson().toJson(
                    new JSONResponseObject(
                            "ERROR",
                            "Invalid parameter"
                    )
            );
        }

    };

    public static Route editArticle = (Request req, Response res) -> {
        String header = req.headers("Authorization");
        if (header == null || !AuthService.isAuthorized(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        Article article = new Article();
        JsonObject o = JsonParser.parseString(req.body()).getAsJsonObject();
        article.setId(Integer.parseInt(req.params(":id")));
        article.setTitle(o.get("title").getAsString());
        article.setContent(o.get("content").getAsString());
        article.setCategoryId(o.get("categoryId").getAsInt());

        String[] tags = o.get("tags").getAsString().split(",");

        article = ArticleService.editArticle(article, tags);

        return new Gson().toJson(
                new JSONResponseObject(
                        "SUCCESS",
                        new Gson().toJsonTree(article)
                )
        );
    };

    public static Route getArticlesPage = (Request req, Response res) -> {
        String pageNumString = req.queryParams("page");
        Integer pageNum;

        if (pageNumString == null) pageNum = 1;
        else {
            try {
                pageNum = Integer.parseInt(pageNumString);
            } catch (NumberFormatException e) {
                return new Gson().toJson(
                        new JSONResponseObject("ERROR", "Invalid parameter")
                );
            }
        }

        List<Article> articles = ArticleService.getArticlesPage(pageNum);
        Integer count = ArticleService.getCount(0, 0);
        JsonObject o = new JsonObject();
        o.addProperty("count", count);
        o.add("articles", new Gson().toJsonTree(articles));

        return new Gson().toJson(
                new JSONResponseObject(
                        "SUCCESS",
                        o
                )
        );
    };

    public static Route getArticlesByCategoryPage = (Request req, Response res) -> {
        Integer catId;
        String pageNumString = req.queryParams("page");
        Integer pageNum;

        try {
            catId = Integer.parseInt(req.params(":catId"));
            if (pageNumString == null) {
                pageNum = 1;
            } else {
                pageNum = Integer.parseInt(pageNumString);
            }
        } catch (NumberFormatException e) {
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Invalid parameter")
            );
        }

        List<Article> articles = ArticleService.getArticlesByCategoryPage(catId, pageNum);
        Integer count = ArticleService.getCount(catId, 0);
        JsonObject o = new JsonObject();
        o.addProperty("count", count);
        o.add("articles", new Gson().toJsonTree(articles));

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", o)
        );
    };

    public static Route getArticlesByTagPage = (Request req, Response res) -> {
        Integer tagId;
        String pageNumString = req.queryParams("page");
        Integer pageNum;

        try {
            tagId = Integer.parseInt(req.params(":tagId"));
            if (pageNumString == null) {
                pageNum = 1;
            } else {
                pageNum = Integer.parseInt(pageNumString);
            }
        } catch (NumberFormatException e) {
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Invalid parameter")
            );
        }

        List<Article> articles = ArticleService.getArticlesByTagPage(tagId, pageNum);
        Integer count = ArticleService.getCount(0, tagId);
        JsonObject o = new JsonObject();
        o.addProperty("count", count);
        o.add("articles", new Gson().toJsonTree(articles));

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", o)
        );
    };

    public static Route getMostRecentArticles = (Request req, Response res) -> {
        List<Article> articles = ArticleService.getMostRecentArticles();

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(articles))
        );
    };

    public static Route getMostArticlesReadMonthly = (Request req, Response res) -> {
        List<Article> articles = ArticleService.getMostReadArticlesMonthly();

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(articles))
        );
    };

}
