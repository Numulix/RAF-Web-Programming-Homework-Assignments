package app;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {

        port(8080);
        enableCORS();

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
            response.header("Access-Control-Request-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            response.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });

    }

}
