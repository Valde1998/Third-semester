package PointsExercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Main {

    static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        EntityManager em = emf.createEntityManager();

        PointDAO pDAO = new PointDAO();

        for (int i = 0; i < 100; i++) {
            Point p = new Point(i, i);
            pDAO.savePoint(p);
        }
        
        System.out.println(pDAO.totalPoints());
        System.out.println(pDAO.getAveragePoints());

        List<Point> points = pDAO.getAllPoint();
        points.stream().forEach(System.out::println);

    }

    public static Long totalPoints(){
        try(EntityManager em = emf.createEntityManager()){
            Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
            System.out.println("Total Points: " + q1.getSingleResult());
            return (Long) q1.getSingleResult();
        }
    }

    public static double getAveragePoints() {
        try (EntityManager em = emf.createEntityManager()) {
            Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
            double average = (Double) q2.getSingleResult();
            return average;
        }
    }

    public static List<Point> getAllPoint() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
            List<Point> results = query.getResultList();
            return results;
        }
    }
}
