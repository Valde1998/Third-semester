package Exercise_Hotels.dtos;

import Exercise_Hotels.model.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoomDTO {

    private Long id;

    private Long hotelId;

    private int number;

    private double price;


    public RoomDTO(Room room) {
        this.id = room.getId();
        this.hotelId = room.getHotel().getId();
        this.number = room.getNumber();
        this.price = room.getPrice();
    }

    public static Set<RoomDTO> getEntities (List<Room> rooms){

        return rooms.stream().map(room -> new RoomDTO(room)).collect(Collectors.toSet());
    }
}
