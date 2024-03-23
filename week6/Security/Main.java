package Security;


import Security.config.ApiConfig;
import Security.model.Hotel;
import Security.model.Role;
import Security.model.Room;
import Security.model.User;
import Security.persistence.HibernateConfig;
import Security.persistence.daos.HotelDAO;
import Security.persistence.daos.IDAO;
import Security.persistence.daos.UserDao;
import Security.routes.AllRoutes;
import jakarta.persistence.EntityManagerFactory;


public class Main {

    private static EntityManagerFactory emf;

    public static void main(String[] args) {

        emf = HibernateConfig.getEntityManagerFactoryConfig("securitydb", false);


        IDAO hotelIDAO = new HotelDAO(emf);

        Hotel hotel = new Hotel("Palads", "Some address 1");

        Room room = new Room(10, 1800.00);
        hotel.addRoom(room);

        hotelIDAO.create(hotel);

        Room roomTest = new Room(11, 1200.00);

        Hotel hotelTest = new Hotel("Test", "Tester street 1");
        hotelTest.addRoom(roomTest);

        hotelIDAO.create(hotelTest);

        //SecurityController securityControllerInstantiated = SecurityController.getInstance();

        UserDao userDao = new UserDao();

        Role role1 = new Role("user");
        Role role2 = new Role("admin");
        userDao.createRole(role1);
        userDao.createRole(role2);

        User user1 = new User("Valde", "123");

        userDao.createUser(user1);

        userDao.addRoleToUser(role2, user1);

        ApiConfig
                .getInstance()
                .initiateServer()
                .setRoutes(AllRoutes.getSecurityRoutes())
                .setRoutes(AllRoutes.getSecuredRoutes())
                .setRoutes(AllRoutes.hotels(emf))
                .setRoutes(AllRoutes.rooms(emf))
                .setGeneralExceptionHandling()
                .startServer(7070)
                .checkSecurityRoles()
                .errorHandling()
                .setApiExceptionHandling();



    }
}
