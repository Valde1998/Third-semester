package cphbusiness.rest;

import dk.cphbusiness.rest.controllers.PersonController;
import dk.cphbusiness.rest.controllers.PersonEntityController;
import dk.cphbusiness.security.SecurityController;
import dk.cphbusiness.security.SecurityRoutes.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose: To demonstrate the use of unprotected routes and protected ones
 *
 * Author: Thomas Hartmann
 */
public class RestRoutes {
    PersonController personController = new PersonController(); // IN memory person collection
    PersonEntityController personEntityController = PersonEntityController.getInstance(); // Person collection in DB
    SecurityController securityController = SecurityController.getInstance();

    public EndpointGroup getOpenRoutes() {
        return () -> {
            path("open", () -> {
                path("person", () -> {
                    get("/", personController.getAll(), Role.ANYONE);
                    get("/{id}", personController.getById(), Role.ANYONE);
                    get("/email/{email}", personController.getByEmail(), Role.ANYONE);
                    post("/", personController.create(), Role.ANYONE);
                    put("/{id}", personController.update(), Role.ANYONE);
                    delete("/{id}", personController.delete(), Role.ANYONE);
                });
            });
        };
    }
//    public EndpointGroup getProtectedPersonRoutes(){
//        return () -> {
//            path("/person", () -> {
//                before(securityController.authenticate()); // This means that there MUST be a token in the header
//                post("/", ctx -> personController.create(), Role.USER);
//                put("/{id}", ctx -> personController.update(), Role.USER);
//                delete("/{id}", ctx -> personController.delete(), Role.USER);
//            });
//        };
//    }

    // Show a different way of getting an EndpointGroup with a lambda expression
    public EndpointGroup personEntityRoutes = ()->{
      path("/person",()-> {
          before(securityController.authenticate());
          get("/", personEntityController.getAll(), Role.ANYONE);
          get("/resetdata", personEntityController.resetData(), Role.ADMIN);
          get("/{id}", personEntityController.getById(), Role.ANYONE);
          post("/", personEntityController.create(), Role.ADMIN);
          put("/{id}", personEntityController.update(), Role.ADMIN);
          delete("/{id}", personEntityController.delete(), Role.ADMIN);
          post("connect/address/{personId}/{addressId}", personEntityController.connectPersonToAddress(), Role.ADMIN);
          post("connect/phone/{personId}/{phoneId}", personEntityController.connectPersonToPhone(), Role.ADMIN);
          post("connect/hobby/{personId}/{hobbyId}", personEntityController.connectPersonToHobby(), Role.ADMIN);

      });
    };
}