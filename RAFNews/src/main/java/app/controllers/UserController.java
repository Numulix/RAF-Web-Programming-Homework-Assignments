package app.controllers;

import app.Application;
import app.entities.User;
import app.entities.response.JSONResponseObject;
import app.services.UserService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

    public static Route addUser = (Request req, Response res) -> {
        User user = new Gson().fromJson(req.body(), User.class);
        System.out.println(user.getName() + " " + user.getSurname());
        user = UserService.addUser(user);

        if (user == null) {
            return new Gson().toJson(
                    new JSONResponseObject("ERROR","Email address taken")
            );
        }

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS")
        );
    };

}
