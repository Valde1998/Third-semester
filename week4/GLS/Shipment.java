package GLS;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date shipmentDate;

    @ManyToOne
    private Package aPackage;

    @ManyToOne
    private Location sourceLocation;

    @ManyToOne
    private Location destinationLocation;






}