package Security.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotelName;

    private String address;


    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    private Set<Room> rooms = new HashSet<>();

    public Hotel(String hotelName, String address) {
        this.hotelName = hotelName;
        this.address = address;
    }
    public Hotel(Long id, String hotelName, String address) {
        this.id = id;
        this.hotelName = hotelName;
        this.address = address;
    }

    public void addRoom(Room room){
        if(room != null){
            rooms.add(room);
        }
        room.setHotel(this);
    }
}
