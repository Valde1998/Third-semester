package Exercise_Hotels.controllers;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import Exercise_Hotels.dao.HotelDAO;
import Exercise_Hotels.dao.IDAO;
import Exercise_Hotels.dtos.HotelDTO;
import Exercise_Hotels.dtos.RoomDTO;
import Exercise_Hotels.model.Hotel;
import Exercise_Hotels.model.Room;

import java.util.*;

public class HotelController {

    EntityManagerFactory emf;

    public HotelController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Handler getAll() {

        return ctx -> {
            IDAO hotelIDAO = new HotelDAO(emf);
            List<Hotel> hotels = hotelIDAO.getAll();

            ctx.status(HttpStatus.OK).json(HotelDTO.getEntities(hotels));
        };
    }

    public Handler getById(){
        return ctx -> {

            int id = Integer.parseInt(ctx.pathParam("id"));

            HotelDAO hotelDAO = new HotelDAO(emf);

            Hotel hotel = hotelDAO.getById(id);

            ctx.status(HttpStatus.OK).json(HotelDTO.getEntity(hotel));
        };
    }

    public Handler getRoomsFromHotelByHotelId(){

        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));

            HotelDAO hotelDAO = new HotelDAO(emf);

            List<Room> rooms = hotelDAO.getAllRoomsFromHotelByHotelId(id);
            Set<RoomDTO> roomDTOS = RoomDTO.getEntities(rooms);
            roomDTOS.forEach(System.out::println);

            ctx.status(HttpStatus.OK).json(RoomDTO.getEntities(rooms));
        };
    }

    public Handler createHotel(){

        return ctx -> {
            HotelDAO hotelDAO = new HotelDAO(emf);
            Hotel hotel = ctx.bodyAsClass(Hotel.class);
            Hotel hotelWithId = hotelDAO.create(hotel);

            ctx.status(201).json("Following hotel has been stored in the databse: " + hotelWithId);
        };
    }




}
