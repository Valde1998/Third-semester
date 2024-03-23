package Security.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import jakarta.persistence.EntityManagerFactory;
import org.weeks.week6.Security.controllers.HotelController;
import org.weeks.week6.Security.controllers.RoomController;
import org.weeks.week6.Security.controllers.SecurityController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AllRoutes {

    private static SecurityController securityController = SecurityController.getInstance();
    private static ObjectMapper jsonMapper = new ObjectMapper();

    public static EndpointGroup getSecurityRoutes() {

        return () -> {
            path("/auth", () -> {
                post("/login", securityController.login(), Role.ANYONE);
                post("/register", securityController.register(), Role.ANYONE);
            });
        };
    }

    public static EndpointGroup getSecuredRoutes() {

        return () -> {
            path("/protected", () -> {
                before(securityController.authenticate());
                get("/user_demo", (ctx) -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from USER Protected")), Role.USER);
                get("/admin_demo", (ctx) -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from ADMIN Protected")), Role.ADMIN);
            });
        };
    }

    public enum Role implements RouteRole {ANYONE, USER, ADMIN}


    public static EndpointGroup hotels(EntityManagerFactory emf) {
        HotelController hotelController = new HotelController(emf);
        return () -> {
            path("/protected", () -> {
                before(securityController.authenticate());
                path("/hotels", () -> {
                    get("/", hotelController.getAll(), Role.USER, Role.ADMIN);
                    post("/", hotelController.createHotel(), Role.ADMIN);
                    path("/{id}", () -> {
                        get(hotelController.getById(), Role.ANYONE);
                        put(hotelController.updateHotel(), Role.ADMIN);
                        delete(hotelController.deleteHotel(), Role.ADMIN);
                        get("/rooms", hotelController.getRoomsFromHotelByHotelId());
                    });
                });
            });
        };
    }

    public static EndpointGroup rooms(EntityManagerFactory emf) {
        RoomController roomController = new RoomController(emf);
        return () -> {
            path("/rooms", () -> {
                get("/", roomController.getAll());
                post("/", roomController.create());
                path("/{id}", () -> {
                    get(roomController.getById());
                    put(roomController.updateRoom());
                    delete(roomController.deleteRoom());
                });
            });
        };
    }
}
