package app.controllers;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import app.entities.Comment;
import app.entities.response.JSONResponseObject;
import app.services.CommentService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class CommentController {

    private static Validator<Comment> commentValidator = ValidatorBuilder.<Comment> of()
            .constraint(Comment::getPostId, "article id", c -> c.notNull()
                .greaterThanOrEqual(1))
            .constraint(Comment::getAuthor, "author", c -> c.notNull()
                .notEmpty()
                .notBlank())
            .constraint(Comment::getContent, "comment", c -> c.notNull()
                .notBlank()
                .notEmpty())
            .build();

    public static Route addComment = (Request req, Response res) -> {
        Comment comment = new Gson().fromJson(req.body(), Comment.class);

        ConstraintViolations cv = commentValidator.validate(comment);
        if (!cv.isValid()) {
            StringBuilder errors = new StringBuilder();
            cv.forEach(x -> errors.append(x.message()+"\n"));
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", errors.toString())
            );
        }

        CommentService.addComment(comment);
        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(comment))
        );
    };

    public static Route getCommentsForArticle = (Request req, Response res) -> {
        List<Comment> comments = CommentService.getCommentsForArticle(Integer.parseInt(req.params(":id")));

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(comments))
        );
    };

}
