package Exercise_Hotels;

import Exercise_Hotels.apiEndpoints.Endpoints;
import Exercise_Hotels.config.ApiConfig;
import Exercise_Hotels.dao.HibernateConfig;
import Exercise_Hotels.dao.HotelDAO;
import Exercise_Hotels.dao.IDAO;
import Exercise_Hotels.dao.RoomDAO;
import Exercise_Hotels.model.Hotel;
import Exercise_Hotels.model.Room;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {

    private static EntityManagerFactory emf;

    public static void main(String[] args) {

        emf = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false);


        IDAO hotelIDAO = new HotelDAO(emf);
        IDAO roomDAO = new RoomDAO(emf);

        Hotel hotel = new Hotel("Hotel 1", " Address 1");

        Room room = new Room(1, 1500.00);
        hotel.addRoom(room);

        hotelIDAO.create(hotel);

        Room roomTest = new Room(2, 1100.00);

        Hotel hotelTest = new Hotel("Test", "Tester street 1");
        hotelTest.addRoom(roomTest);

        hotelIDAO.create(hotelTest);


        System.out.println("\nALL HOTELS");
        List<Hotel> allHotels = hotelIDAO.getAll();
        allHotels.forEach(System.out::println);

        HotelDAO hotelDAO = new HotelDAO(emf);
        List<Room> rooms = hotelDAO.getAllRoomsFromHotelByHotelId(2);
        rooms.forEach(System.out::println);


        ApiConfig
                .getInstance()
                .initiateServer()
                .errorHandling()
                .startServer(7070)
                .setRoutes(Endpoints.hotels(emf))
                .setRoutes(Endpoints.rooms(emf));
    }

}
