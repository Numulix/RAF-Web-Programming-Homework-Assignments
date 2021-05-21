package app.controllers;

import app.Application;
import app.auth.AuthService;
import app.entities.User;
import app.services.UserService;
import app.util.UtilMethods;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.digest.DigestUtils;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {

    public static Route login = (Request req, Response res) -> {

        JsonObject object = JsonParser.parseString(req.body()).getAsJsonObject();
        String email = object.get("email").getAsString();
        String password = DigestUtils.sha256Hex(object.get("password").getAsString());

        User user = UserService.findUser(email);
        if (user != null && user.getPassword().equals(password)) {
            return AuthService.generateJWT(user);
        }

        res.status(403);
        return "Invalid credentials";
    };

    public static Route adminTest = (Request req, Response res) -> {
        String header = req.headers("Authorization");

        if (UtilMethods.isEmpty(header)) {
            res.status(403);
            return "Unauthorized";
        }
        return AuthService.isAdmin(header);
    };

    public static Route authTest = (Request req, Response res) -> {
        String header = req.headers("Authorization");

        if (UtilMethods.isEmpty(header)) {
            res.status(403);
            return "Unauthorized";
        }

        return AuthService.isAuthorized(header);
    };

}
