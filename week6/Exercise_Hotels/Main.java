package Exercise_Hotels;

import Exercise_Hotels.config.ApiConfig;
import Exercise_Hotels.config.HibernateConfig;
import Exercise_Hotels.controllers.HotelController;
import Exercise_Hotels.dao.HotelDAO;
import Exercise_Hotels.dao.IDAO;
import Exercise_Hotels.dao.RoomDAO;
import Exercise_Hotels.model.Hotel;
import Exercise_Hotels.model.Room;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import java.util.*;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    private static EntityManagerFactory emf;

    public static void main(String[] args) {

        emf = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false);


        IDAO hotelIDAO = new HotelDAO(emf);

        Hotel hotel = new Hotel("Hotel 1", "address 1");

        Room room = new Room(1, 1000.00);
        hotel.addRoom(room);

        hotelIDAO.create(hotel);

        Room roomTest = new Room(5, 1500.00);

        Hotel hotelTest = new Hotel("Test", "Tester street 1");
        hotelTest.addRoom(roomTest);

        hotelIDAO.create(hotelTest);

        System.out.println("\n---ALL HOTELS---");
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
                .setRoutes(hotelsAndRooms());
    }

    private static EndpointGroup hotelsAndRooms() {
        HotelController hotelController = new HotelController(emf);
        return () -> {
            path("/hotels", () -> {
                get("/", hotelController.getAll());
                get("/{id}", hotelController.getById());
                get("/{id}/rooms", hotelController.getRoomsFromHotelByHotelId());
                post("/", hotelController.createHotel());
            });
        };
    }

}

   

   
