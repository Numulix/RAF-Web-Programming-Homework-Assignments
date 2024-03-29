package app.controllers;

import app.Application;
import app.auth.AuthService;
import app.entities.JwtResponse;
import app.entities.User;
import app.entities.response.JSONResponseObject;
import app.services.UserService;
import app.util.UtilMethods;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.digest.DigestUtils;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class LoginController {

    public static Route login = (Request req, Response res) -> {
        try {
            JsonObject object = JsonParser.parseString(req.body()).getAsJsonObject();
            String email = object.get("email").getAsString();
            String password = DigestUtils.sha256Hex(object.get("password").getAsString());

            User user = UserService.findUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                return new Gson().toJson(
                        new JSONResponseObject("SUCCESS",
                                new Gson().toJsonTree(new JwtResponse(AuthService.generateJWT(user))))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        res.status(403);
        return new Gson().toJson(
                new JSONResponseObject("ERROR", "Invalid Credentials")
        );
    };

    public static Route adminTest = (Request req, Response res) -> {
        String header = req.headers("Authorization");

        if (UtilMethods.isEmpty(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        JsonObject o = new JsonObject();
        o.addProperty("isAdmin", AuthService.isAdmin(header));
        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", o)
        );
    };

    public static Route authTest = (Request req, Response res) -> {
        String header = req.headers("Authorization");

        if (UtilMethods.isEmpty(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        JsonObject o = new JsonObject();
        o.addProperty("isAdmin", AuthService.isAuthorized(header));
        return new Gson().toJson(
                new JSONResponseObject("SUCCESS", o)
        );
    };

}
