package veterinarien.DTOs;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private int id;
    private String veterinarianName;
    private PatientDTO patientDTO;
    private String date;
    private String description;

}