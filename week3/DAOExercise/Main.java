package DAOExercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.buildEntityFactoryConfig();
        EntityManager em = emf.createEntityManager();

        StudentDAO studentDAO = new StudentDAO();
        Student student = new Student("Thomas", "CC", "hurtig@email.com", 38);
        studentDAO.save(student);
        studentDAO.save(new Student("Emil", "Jensen", "Emil@email.com", 24));
        studentDAO.save(new Student("Johan", "Johansen", "johan@email.com", 19));

        List<Student> students = studentDAO.findAll();
        students.forEach(System.out::println);

        Student studentToUpdate = studentDAO.findById(1);
        studentToUpdate.setAge(30);
        studentDAO.update(studentToUpdate);

        students = studentDAO.findAll();
        students.forEach(System.out::println);

        studentDAO.delete(3);

        students = studentDAO.findAll();
        students.forEach(System.out::println);

        em.close();
        emf.close();

    }
}