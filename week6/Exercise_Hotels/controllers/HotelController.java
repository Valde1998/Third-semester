package Exercise_Hotels.controllers;

import Exercise_Hotels.dao.HotelDAO;
import Exercise_Hotels.dao.IDAO;
import Exercise_Hotels.dtos.HotelDTO;
import Exercise_Hotels.dtos.RoomDTO;
import Exercise_Hotels.model.Hotel;
import Exercise_Hotels.model.Room;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;


import java.util.List;
import java.util.Set;

public class HotelController {

    EntityManagerFactory emf;

    public HotelController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Handler getAll() {

        return ctx -> {
            IDAO hotelIDAO = new HotelDAO(emf);
            List<Hotel> hotels = hotelIDAO.getAll();

            ctx.status(200).json(HotelDTO.getEntities(hotels));
        };
    }

    public Handler getById(){
        return ctx -> {

            Long id = Long.parseLong(ctx.pathParam("id"));

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


    public Handler updateHotel() {

        return ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            HotelDAO hotelDAO = new HotelDAO(emf);
            Hotel hotelBeforeUpdate = hotelDAO.getById(id);

            Hotel hotel = ctx.bodyAsClass(Hotel.class);
            hotel.setId(id);

            // Should maybe be converted to a DTO list
            Hotel hotelUpdated = hotelDAO.update(hotel);

            ctx.status(201).json("Hotel updated (before update): " + hotelBeforeUpdate
                    + "\nHotel after update: " + hotelUpdated);
        };
    }

    public Handler deleteHotel() {

        return ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            HotelDAO hotelDAO = new HotelDAO(emf);

            Hotel hotelToDelete = hotelDAO.getById(id);
            HotelDTO hotelDeleted = new HotelDTO(hotelToDelete);

            hotelDAO.delete(hotelToDelete);

            ctx.status(200).json(hotelDeleted);
        };
    }
}
