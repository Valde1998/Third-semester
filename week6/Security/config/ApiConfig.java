package Security.config;

import Security.controllers.SecurityController;
import Security.dtos.UserDTO;
import Security.exceptions.ApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.HttpStatus;


import java.util.Set;
import java.util.stream.Collectors;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ApiConfig {


    private static ObjectMapper objectMapper = new ObjectMapper();
    private static SecurityController securityController = SecurityController.getInstance();
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


    public ApiConfig checkSecurityRoles() {

        app.updateConfig(config -> {

            config.accessManager((handler, ctx, permittedRoles) -> {

                Set<String> allowedRoles = permittedRoles.stream().map(role -> role.toString().toUpperCase()).collect(Collectors.toSet());

                if(allowedRoles.contains("ANYONE") || ctx.method().toString().equals("OPTIONS")) {
                    // Allow requests from anyone and OPTIONS requests (preflight in CORS)
                    handler.handle(ctx);
                    return;
                }

                UserDTO user = ctx.attribute("user");
                System.out.println("USER IN CHECK_SEC_ROLES: "+user);
                if(user == null)
                    ctx.status(HttpStatus.FORBIDDEN)
                            .json(objectMapper.createObjectNode()
                                    .put("msg","Not authorized. No username were added from the token"));


                if (securityController.authorize(user, allowedRoles))
                    handler.handle(ctx);
                else
                    throw new ApiException(HttpStatus.FORBIDDEN.getCode(), "Unauthorized with roles: "+allowedRoles);
            });
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


    public ApiConfig setApiExceptionHandling() {
        // Might be overruled by the setErrorHandling method
        app.exception(ApiException.class, (e, ctx) -> {
            int statusCode = e.getStatusCode();
            System.out.println("Status code: " + statusCode + ", Message: " + e.getMessage());
            var on = objectMapper
                    .createObjectNode()
                    .put("status", statusCode)
                    .put("msg", e.getMessage());
            ctx.json(on);
            ctx.status(statusCode);
        });
        return instance;
    }
    public ApiConfig setGeneralExceptionHandling(){
        app.exception(Exception.class, (e,ctx)->{
            e.printStackTrace();
            ctx.result(e.getMessage());
        });
        return instance;
    }

    public void stopServer(){
        app.stop();
    }

}

