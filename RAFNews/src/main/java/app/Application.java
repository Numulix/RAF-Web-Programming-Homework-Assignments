package app;

import app.controllers.*;
import app.entities.Comment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static spark.Spark.*;

public class Application {

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static void main(String[] args) {
        System.out.println("Port " + args[0]);
        port(Integer.parseInt(args[0]));
        enableCORS();
        path("/api", () -> {
            path("/user", () -> {
                post("/add", UserController.addUser);
                post("/login", LoginController.login);
                get("/admintest", LoginController.adminTest);
                get("/authtest", LoginController.authTest);
                put("/:id", UserController.editUser);
            });

            delete("/user/:id", UserController.deleteUser);
            get("/users", UserController.getUsers);
            get("/user/:id", UserController.getUserById);

            post("/comment", CommentController.addComment);
            get("/comments/:id", CommentController.getCommentsForArticle);

            post("/category", CategoryController.addCategory);
            delete("/category/:id", CategoryController.deleteCategory);
            put("/category/:id", CategoryController.updateCategory);
            get("/category", CategoryController.allCategories);
            get("/category/:id", CategoryController.getSingleCategory);

            post("/article", ArticleController.addArticle);
            get("/article/:id", ArticleController.getSingleArticle);
            delete("/article/:id", ArticleController.deleteArticle);
            put("/article/:id", ArticleController.editArticle);
            get("/articles", ArticleController.getArticlesPage);
            get("/articles/:catId", ArticleController.getArticlesByCategoryPage);
            get("/articles/tag/:tagId", ArticleController.getArticlesByTagPage);
            get("/recent", ArticleController.getMostRecentArticles);
            get("/popular", ArticleController.getMostArticlesReadMonthly);

            get("/tags", TagController.allTags);
            get("/tags/:postId", TagController.tagsForPost);

            get("/health-check", (req, res) -> {
                res.status(200);
                return "OK";
            });
        });

    }

    /*
        Source: Spark Java tutorial
     */
    private static void enableCORS() {

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Request-Method", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            response.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            response.header("Access-Control-Allow-Credentials", "true");
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });

    }

}
