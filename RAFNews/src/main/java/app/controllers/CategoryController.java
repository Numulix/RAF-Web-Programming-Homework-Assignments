package app.controllers;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import app.Application;
import app.auth.AuthService;
import app.entities.Category;
import app.entities.response.JSONResponseObject;
import app.services.CategoryService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;

public class CategoryController {

    private static Validator<Category> categoryValidator = ValidatorBuilder.<Category> of()
            .constraint(Category::getCategoryName, "name", c -> c.notNull()
                .notBlank()
                .notEmpty())
            .constraint(Category::getCategoryDescription, "description", c -> c.notNull()
                .notEmpty()
                .notNull())
            .build();

    public static Route addCategory = (Request req, Response res) -> {
      String header = req.headers("Authorization");
      if (header == null || !AuthService.isAuthorized(header)) {
          res.status(403);
          return new Gson().toJson(
                  new JSONResponseObject("ERROR", "Unauthorized")
          );
      }

      Category category = Application.gson.fromJson(req.body(), Category.class);

      ConstraintViolations cv = categoryValidator.validate(category);
        if (!cv.isValid()) {
            StringBuilder errors = new StringBuilder();
            cv.forEach(x -> errors.append(x.message()+"\n"));
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", errors.toString())
            );
        }

      CategoryService.addCategory(category);

      return new Gson().toJson(
              new JSONResponseObject(
                      "SUCCESS",
                      new Gson().toJsonTree(category)
              )
      );
    };

    public static Route deleteCategory = (Request req, Response res) -> {
        String header = req.headers("Authorization");
        if (header == null || !AuthService.isAuthorized(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        boolean bool = CategoryService.deleteCategory(Integer.parseInt(req.params(":id")));
        System.out.println(bool);
        if (bool) {
            return new Gson().toJson(
                    new JSONResponseObject("SUCCESS")
            );
        }

        return new Gson().toJson(
                new JSONResponseObject(
                        "ERROR",
                        "Category not deleted. One or more articles exist with this category")
        );
    };

    public static Route updateCategory = (Request req, Response res) -> {
        String header = req.headers("Authorization");
        if (header == null || !AuthService.isAuthorized(header)) {
            res.status(403);
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", "Unauthorized")
            );
        }

        Category category = Application.gson.fromJson(req.body(), Category.class);
        System.out.println(category.getCategoryName() + " " + category.getCategoryDescription());

        ConstraintViolations cv = categoryValidator.validate(category);
        if (!cv.isValid()) {
            StringBuilder errors = new StringBuilder();
            cv.forEach(x -> errors.append(x.message()+"\n"));
            return new Gson().toJson(
                    new JSONResponseObject("ERROR", errors.toString())
            );
        }

        category = CategoryService.updateCategory(category);

        return new Gson().toJson(
                new JSONResponseObject(
                        "SUCCESS",
                        new Gson().toJsonTree(category)
                )
        );
    };

    // U daljim rutama nema potrebe za autentifikacijom jer su potrebni za javnu platformu
    public static Route allCategories = (Request req, Response res) -> {
        ArrayList<Category> categories = new ArrayList<>();

        categories.addAll(CategoryService.allCategories());
        return new Gson().toJson(
                new JSONResponseObject(
                        "SUCCESS",
                        new Gson().toJsonTree(categories)
                )
        );
    };

    public static Route getSingleCategory = (Request req, Response res) -> {
        Category category = CategoryService.getSingleCategory(Integer.parseInt(req.params(":id")));

        if (category != null) {
            return new Gson().toJson(
                    new JSONResponseObject(
                            "SUCCESS",
                            new Gson().toJsonTree(category)
                    )
            );
        }

        return new Gson().toJson(
                new JSONResponseObject(
                        "ERROR",
                        "No category found with given ID"
                )
        );
    };
    //-----------------------------------------------------------------------------------------
}
