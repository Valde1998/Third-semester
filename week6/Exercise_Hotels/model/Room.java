package Exercise_Hotels.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private double price;

    @ManyToOne
    private Hotel hotel;

    public Room(int number, double price) {
        this.number = number;
        this.price = price;
    }

}

