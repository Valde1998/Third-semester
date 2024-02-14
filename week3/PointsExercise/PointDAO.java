package PointsExercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PointDAO {

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

    public void savePoint(Point p) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }
    }

    public Point getPoint(int id){
        try(EntityManager em = emf.createEntityManager()){
            Point point = em.find(Point.class, id);
            return point;
        }
    }

    public Long totalPoints(){
        try(EntityManager em = emf.createEntityManager()){
            Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
            System.out.println("Total Points: " + q1.getSingleResult());
            return (Long) q1.getSingleResult();
        }
    }

    public double getAveragePoints() {
        try (EntityManager em = emf.createEntityManager()) {
            Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
            double average = (Double) q2.getSingleResult();
            return average;
        }
    }

    public List<Point> getAllPoint() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
            List<Point> results = query.getResultList();
            return results;
        }
    }
}
