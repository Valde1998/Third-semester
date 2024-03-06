package veterinarien.DTOs;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import veterinarien.Controller.AppointmentController;
import veterinarien.Controller.PatientController;

import java.time.LocalDateTime;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static Map<Integer, AppointmentDTO> appointments = new LinkedHashMap<>();
    public static Map<Integer, PatientDTO> patients = new HashMap<>();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
        app.before(ctx -> {
            app.attribute("testAttribute", "Hello :)");
            System.out.println(ctx.req());
        });

        app.after(ctx -> {
            System.out.println(app.attribute("testAttribute").toString());
            System.out.println(ctx.res());
        });

        //create patients:
        PatientDTO patient1 = new PatientDTO(1, "Samantha", new String[]{"Cat dander", "Pollen"}, new String[]{"Penicillin"});
        PatientDTO patient2 = new PatientDTO(2, "Xavier", new String[]{"Peanuts", "Grass pollen"}, new String[]{"Epinephrine"});
        PatientDTO patient3 = new PatientDTO(3, "Isabella", new String[]{"Pollen", "Peanuts"}, new String[]{"None"});
        PatientDTO patient4 = new PatientDTO(4, "Elijah", new String[]{"Pollen", "Dust mites"}, new String[]{"Ibuprofen"});

        //create appointments:
        AppointmentDTO appointment1 = new AppointmentDTO(1, "A. Smith", patient1, LocalDateTime.now().toString(), "Allergy testing");
        AppointmentDTO appointment2 = new AppointmentDTO(2, "B. Johnson", patient2, LocalDateTime.now().toString(), "Annual physical exam");
        AppointmentDTO appointment3 = new AppointmentDTO(3, "C. Williams", patient3, LocalDateTime.now().toString(), "Flu shot");
        AppointmentDTO appointment4 = new AppointmentDTO(4, "D. Brown", patient4, LocalDateTime.now().toString(), "Dental cleaning");

        patients.put(1, patient1);
        patients.put(2, patient2);
        patients.put(3, patient3);
        patients.put(4, patient4);

        appointments.put(1, appointment1);
        appointments.put(2, appointment2);
        appointments.put(3, appointment3);
        appointments.put(4, appointment4);

        app.routes(appointmentRessource());
        app.routes(patientRessource());

    }

    private static EndpointGroup appointmentRessource() {
        return () -> {
            path("/api/vet", () -> {
                path("/appointments", () -> {
                    get("/", AppointmentController.getAllAppointments());
                    get("/appointment/{id}", AppointmentController.getAppointmentById());
                });
            });
        };
    }

    private static EndpointGroup patientRessource(){
        return () -> {
            path("/api/vet", () -> {
                path("/patients", () -> {
                    get("/", PatientController.getAllPatients());
                    get("/patient/{id}", PatientController.getPatientById());
                });
            });
        };
    }

}
