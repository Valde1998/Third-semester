package Security.dtos;


import Security.model.Hotel;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class HotelDTO {

    private Long id;
    private String hotelName;
    private String address;

    @JsonIgnore
    private List<RoomDTO> roomDTOS;

    public HotelDTO(Hotel hotel) {
        this.id = hotel.getId();
        this.hotelName = hotel.getHotelName();
        this.address = hotel.getAddress();
    }

    public static Set<HotelDTO> getEntities(List<Hotel> hotels) {
        return hotels.stream().map(hotel -> new HotelDTO(hotel)).collect(Collectors.toSet());
    }
    public static HotelDTO getEntity(Hotel hotel){
        return new HotelDTO(hotel);
    }


}
