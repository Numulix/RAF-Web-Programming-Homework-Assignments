package app.controllers;

import app.Application;
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

}
