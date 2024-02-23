package RecyclingExercise.Model;

import RecyclingExercise.DAO.DriverDAOImpl;
import RecyclingExercise.DAO.IWasteTruckDAO;
import RecyclingExercise.DAO.WasteTruckDAOImpl;
import RecyclingExercise.Model.WasteTruck;
import RecyclingExercise.Model.Driver;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        WasteTruckDAOImpl wasteTruckDAO = new WasteTruckDAOImpl(false);
        DriverDAOImpl driverDAO = new DriverDAOImpl(false);


        wasteTruckDAO.saveWasteTruck("Volkswagen", "DK 2354535",5);
        String driverId = driverDAO.saveDriver("Mike", "Påtaget", new BigDecimal(25000));
        wasteTruckDAO.addDriverToWasteTruck(wasteTruckDAO.getWasteTruckById(1),driverDAO.getDriverById(driverId));

    }
}
