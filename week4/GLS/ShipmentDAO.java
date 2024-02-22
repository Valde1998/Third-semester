package GLS;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ShipmentDAO {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

    public void save(Shipment shipment) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(shipment);
        em.getTransaction().commit();
        em.close();
    }

    public Shipment findById(int id) {
        EntityManager em = emf.createEntityManager();
        Shipment foundShipment = em.find(Shipment.class, id);
        em.close();
        return foundShipment;
    }

    public void update(Shipment shipment) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(shipment);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Shipment shipment = findById(id);
        if (shipment != null) {
            em.remove(shipment);
        }
        em.getTransaction().commit();
        em.close();
    }
}
