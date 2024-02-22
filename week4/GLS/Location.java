package GLS;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "sourceLocation")
    private Set<Shipment> shipments1 = new HashSet<>();

    @OneToMany(mappedBy = "destinationLocation")
    private Set<Shipment> shipments2 = new HashSet<>();




}