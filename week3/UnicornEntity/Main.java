package UnicornEntity;

import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        UnicornDAO unicornDAO = UnicornDAO.getInstance(emf);
        Unicorn unicorn = new Unicorn("Frantis",88,105);

        int id = unicornDAO.save(unicorn);
        unicornDAO.save(new Unicorn("Ole",26,77));
        unicornDAO.save(new Unicorn("Warming",219,83));
        System.out.println(id);
        Unicorn found = unicornDAO.findById(1);
        System.out.println("Found name: "+ found.getName());

        found.setName("Thomas");
        Unicorn updated = unicornDAO.update(found);
        System.out.println("Updated name: "+updated.getName());

        Unicorn found2 = unicornDAO.findById(1);

        found2.setName("TEST");

        Unicorn updated2 = unicornDAO.update(found2,found2.getId());

        System.out.println("Name for test is now: "+updated2.getName());

        List<Unicorn> unicorns = unicornDAO.findALl();

        unicorns.forEach(System.out::println);

        unicornDAO.close();
    }
}

