package app.controllers;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import app.Application;
import app.auth.AuthService;
import app.entities.User;
import app.entities.response.JSONResponseObject;
import app.services.UserService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class UserController {

    private static Validator<User> userValidator = ValidatorBuilder.<User> of()
            .constraint(User::getName, "name", c -> c.notNull()
                .notBlank()
                .notEmpty())
            .constraint(User::getEmail, "email", c -> c.notNull()
                .notEmpty()
                .notBlank()
                .email())
            .constraint(User::getSurname, "surname", c -> c.notNull()
                .notBlank()
                .notEmpty())
            .constraint(User::getPassword, "password", c -> c.notNull()
                .notEmpty()
                .notBlank())
            .build();

    public static Route addUser = (Request req, Response res) -> {
        String header = req.headers("Authorization");
        if (header == null || !AuthService.isAdmin(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        User user = new Gson().fromJson(req.body(), User.class);

        ConstraintViolations cv = userValidator.validate(user);
        if (!cv.isValid()) {
            StringBuilder errors = new StringBuilder();
            cv.forEach(x -> errors.append(x.message()+"\n"));
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", errors.toString())
            );
        }

//        System.out.println(user.getName() + " " + user.getSurname());
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

    public static Route deleteUser = (Request req, Response res) -> {
        return null;
    };

    public static Route editUser = (Request req, Response res) -> {
        String header = req.headers("Authorization");
        if (header == null || !AuthService.isAdmin(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        User user = new Gson().fromJson(req.body(), User.class);
        user.setId(Integer.parseInt(req.params(":id")));

        ConstraintViolations cv = userValidator.validate(user);
        if (!cv.isValid()) {
            StringBuilder errors = new StringBuilder();
            cv.forEach(x -> errors.append(x.message()+"\n"));
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", errors.toString())
            );
        }

        user = UserService.editUser(user);

        if (user == null) {
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Email address already taken")
            );
        }

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(user))
        );
    };

    public static Route getUsers = (Request req, Response res) -> {
        List<User> users = UserService.allUsers();
        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(users))
        );
    };

    public static Route getUserById = (Request req, Response res) -> {
        User user = UserService.findUser(Integer.parseInt(req.params(":id")));

        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", new Gson().toJsonTree(user))
        );
    };

}
