package SchoolExercise.Model;

import SchoolExercise.Config.HibernateConfig;
import SchoolExercise.Model.Semester;
import SchoolExercise.Model.Student;
import SchoolExercise.Model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("student_db",false);
        EntityManager em = emf.createEntityManager();


        Semester semester1 = new Semester("Fall 2022", "Fall semester 2022");
        Semester semester2 = new Semester("Spring 2023", "Spring semester 2023");
        Semester semester3 = new Semester("Summer 2023", "Summer semester 2023");
        Student student1 = new Student("David", "Johnson");
        Student student2 = new Student("Samantha", "Brown");
        Student student3 = new Student("Ethan", "Miller");
        Student student4 = new Student("Ava", "Garcia");
        Teacher teacher1 = new Teacher("Michael", "Thompson");
        Teacher teacher2 = new Teacher("Jessica", "Anderson");
        Teacher teacher3 = new Teacher("Jacob", "Lee");
        semester1.addTeacher(teacher1);
        semester1.addTeacher(teacher2);
        semester2.addTeacher(teacher2);
        semester3.addTeacher(teacher3);
        semester1.addStudent(student1);
        semester1.addStudent(student2);
        semester2.addStudent(student3);
        semester3.addStudent(student4);


        try {
            em.getTransaction().begin();
            em.persist(semester1);
            em.persist(semester2);
            em.persist(semester3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}