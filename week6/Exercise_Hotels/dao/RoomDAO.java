package Exercise_Hotels.dao;

import Exercise_Hotels.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


import java.util.List;

public class RoomDAO extends DAO<Room> {


    public RoomDAO(EntityManagerFactory emf) {
        super(emf);
    }


    public Room create(Room entity){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            Long generatedId = entity.getId();
            return em.find(Room.class, generatedId);
        }
    }
    public List<Room> getAll(){

        try(var em = emf.createEntityManager()){
            var query = em.createQuery("SELECT a FROM Room a", Room.class);
            return query.getResultList();
        }
    }

    @Override
    public Room getById(int id) {

        try(var em = emf.createEntityManager()){
            return em.find(Room.class, id);
        }
    }

}
