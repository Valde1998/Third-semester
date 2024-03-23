package Security.persistence.daos;

import Security.exceptions.ValidationException;
import Security.model.Role;
import Security.model.User;
import Security.persistence.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;


import java.util.Set;

public class UserDao {


    private EntityManagerFactory emf;

    public UserDao() {

        this.emf = HibernateConfig.getEntityManagerFactoryConfig("securitydb", false);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVerifiedUser(String username, String password) throws ValidationException {

        try (EntityManager em = getEntityManager()) {

            User user = em.find(User.class, username);

            if (user == null) {
                throw new EntityNotFoundException("No user found with username: " + username);
            }
            user.getRoles().size();

            if (!user.verifyPassword(password)) {
                throw new ValidationException("Wrong password");
            }

            return user;
        }
    }

    public User createUser(String username, String password, Set<String> roles) {

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            User user = new User(username, password);

            for (String role : roles){
                Role userRole = em.find(Role.class, role);
                user.addRole(userRole);
            }

            em.persist(user);

            em.getTransaction().commit();

            return user;
        }
    }

    public User createUser(User user) {

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            em.persist(user);

            em.getTransaction().commit();

            return user;
        }
    }

    public void addRoleToUser(Role role, User user){

        try(EntityManager em = emf.createEntityManager()){

            em.getTransaction().begin();

            user.addRole(role);

            em.merge(user);

            em.getTransaction().commit();
        }


    }
    public void createRole(Role role){

        try(EntityManager em = emf.createEntityManager()){

            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
        }
    }
}
