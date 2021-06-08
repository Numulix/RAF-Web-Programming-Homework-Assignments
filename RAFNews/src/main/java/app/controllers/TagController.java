package app.controllers;

import app.entities.Tag;
import app.entities.response.JSONResponseObject;
import app.services.TagService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class TagController {

    public static Route allTags = (Request req, Response res) -> {
        List<Tag> tags = TagService.allTags();

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(tags))
        );
    };

    public static Route tagsForPost = (Request req, Response res) -> {
        List<Integer> tagIds = TagService.tagsForPost(Integer.parseInt(req.params(":postId")));

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(tagIds))
        );
    };

}
