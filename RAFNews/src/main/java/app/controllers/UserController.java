package app.controllers;

import app.Application;
import app.entities.User;
import app.services.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

    public static Route addUser = (Request req, Response res) -> {
        User user = Application.gson.fromJson(req.body(), User.class);
        System.out.println(user.getName() + " " + user.getSurname());
        user = UserService.addUser(user);

        return Application.gson.toJson(user);
    };

}
