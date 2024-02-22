package RecyclingExercise.DAO;

import RecyclingExercise.Model.WasteTruck;
import RecyclingExercise.Model.Driver;

import java.util.List;

public interface IWasteTruckDAO {
    // WasteTruck
    void saveWasteTruck(String brand, String registrationNumber, int capacity);
    WasteTruck getWasteTruckById(int id);
    void setWasteTruckAvailable(WasteTruck wasteTruck, boolean available);
    void deleteWasteTruck(int id);
    void addDriverToWasteTruck(WasteTruck wasteTruck, Driver driver);
    void removeDriverFromWasteTruck(WasteTruck wasteTruck, String id);
    List<WasteTruck> getAllAvailableTrucks();
}
