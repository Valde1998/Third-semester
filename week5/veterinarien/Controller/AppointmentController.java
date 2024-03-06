package veterinarien.Controller;

import io.javalin.http.Handler;
import veterinarien.DTOs.Main;


public class AppointmentController {

    public static Handler getAllAppointments(){
        return ctx -> {
            ctx.json(Main.appointments);
        };
    }

    public static Handler getAppointmentById(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if (!Main.appointments.containsKey(id)) {
                ctx.status(404);
            }
            ctx.json(Main.appointments.get(id));
        };
    }
}