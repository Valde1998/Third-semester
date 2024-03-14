package Exercise_Hotels.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ApiConfig {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static ApiConfig instance;
    private static Javalin app;

    private ApiConfig() {
    }

    public static ApiConfig getInstance() {
        if (instance == null) {
            instance = new ApiConfig();
        }
        return instance;
    }


    public ApiConfig initiateServer() {

        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.routing.contextPath = "api";
        });
        return instance;
    }

    public ApiConfig setRoutes(EndpointGroup routes) {
        app.routes(() -> {
            path("/", routes);
        });
        return instance;
    }

    public ApiConfig startServer(int port){
        app.start(port);
        return instance;
    }

    public ApiConfig errorHandling(){

        app.error(404, ctx -> {
            String message = ctx.attribute("message");
            message = "{\"404\": \"" + message + "\"}";
            ctx.json(message);
        });

        app.error(500, ctx -> {
            String message = ctx.attribute("message");
            message = "{\"500\": \"" + message + "\"}";
            ctx.json(message);
        });

        return instance;
    }

}

