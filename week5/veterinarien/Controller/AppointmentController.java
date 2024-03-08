package veterinarien.Controller;

import io.javalin.http.Handler;
import veterinarien.DTOs.Main;


public class AppointmentController {

    public static Handler getAllAppointments(){
        if(Main.appointments.isEmpty()){
            return ctx -> ctx.status(404);
        }
        return ctx -> {
            ctx.json(Main.appointments);
        };
    }

    // get=200
    //post=201
    //put=200--201
    //delete=204
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