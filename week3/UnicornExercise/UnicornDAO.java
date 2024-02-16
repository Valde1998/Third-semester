package UnicornExercise;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UnicornDAO {

    private static EntityManagerFactory emf;
    private static UnicornDAO instance;

    public static UnicornDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new UnicornDAO();
        }
        return instance;
    }


    public int save (Unicorn unicorn){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(unicorn);
            em.getTransaction().commit();
            return unicorn.getId();
        }
    }

    public Unicorn update(Unicorn unicorn){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Unicorn updated = em.merge(unicorn);
            em.getTransaction().commit();
            return updated;
        }
    }

    public Unicorn update(Unicorn in,int id){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();

            Unicorn found = em.find(Unicorn.class,id);
            if(found != null){
                Unicorn updated = em.merge(in);
                em.getTransaction().commit();
                return updated;
            }
            return null;
        }
    }

    public Unicorn findById(int id){
        try(var em = emf.createEntityManager();){
            Unicorn found = em.find(Unicorn.class,id);
            return found;
        }
    }

    public void delete(int id){
        try(var em = emf.createEntityManager();){
            em.getTransaction().begin();
            Unicorn unicorn = findById(id);
            if(unicorn != null){
                em.remove(unicorn);
            }
            em.getTransaction().commit();
        }
    }

    public List<Unicorn> findALl(){
        try(var em = emf.createEntityManager()){
            var query = em.createQuery("select u from Unicorn u", Unicorn.class);
            return query.getResultList();
        }
    }

    public void close(){
        emf.close();
    }

}