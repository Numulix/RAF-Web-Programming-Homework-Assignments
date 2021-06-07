package app.controllers;

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

public class ArticleController {

    public static Route addArticle = (Request req, Response res) -> {
        String header = req.headers("Authentication");
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
        String header = req.headers("Authentication");
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
        String header = req.headers("Authentication");
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

}
