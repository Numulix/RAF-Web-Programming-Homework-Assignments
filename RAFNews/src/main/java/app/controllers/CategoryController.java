package app.controllers;

import app.Application;
import app.auth.AuthService;
import app.entities.Category;
import app.services.CategoryService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CategoryController {

    public static Route addCategory = (Request req, Response res) -> {
      String header = req.headers("Authentication");
      if (header == null || !AuthService.isAuthorized(header)) {
          res.status(403);
          return "Unauthorized";
      }

      Category category = Application.gson.fromJson(req.body(), Category.class);
      CategoryService.addCategory(category);

      return Application.gson.toJson(category);
    };

}
