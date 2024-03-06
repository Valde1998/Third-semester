package veterinarien.DTOs;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private int id;
    private String name;
    private String[] allergies;
    private String[] medications;

}
