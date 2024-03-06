package veterinarien.DTOs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

public class ApplicationConfig {

    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static ApplicationConfig appConfig;

    private static Javalin app;

    private ApplicationConfig(){}

    public static ApplicationConfig getInstance(){
        if(appConfig == null){
            appConfig = new ApplicationConfig();
        }
        return appConfig;
    }

    public ApplicationConfig initiateServer(){
        app = Javalin.create(config -> {
            config.http.defaultContentType="application/json";
            config.routing.contextPath="/api";
        });
        return appConfig;
    };


    public ApplicationConfig setRoutes(EndpointGroup routes){
        app.routes(routes);
        return appConfig;
    }

    public ApplicationConfig startServer( int port){
        app.start(port);
        return appConfig;
    };

    public ApplicationConfig setExceptionHandling(){
        app.exception(Exception.class, (e, ctx) -> {
            ObjectNode json = jsonMapper.createObjectNode();
            json.put("errorMessage",e.getMessage());
            ctx.status(500).json(json);
        });
        return appConfig;
    }




}