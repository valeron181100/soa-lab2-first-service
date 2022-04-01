package valeron.bondar.database;

import valeron.bondar.model.FuelType;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.VehicleFields;

import java.util.List;

public interface CustomVehicleRepo {
    List<Vehicle> findAll(VehicleFields sortField, boolean isDistinctOrder, String filters);
    List<Vehicle> findAll(Integer startIndex, Integer maxResults, VehicleFields sortField, boolean isDistinctOrder, String filters);
    Long totalCount();
    double countAvgNumberOfWheels();
    List<Vehicle> findByName(String nameInput, VehicleFields sortField, boolean isDistinctOrder);
    List<Vehicle> findByName(String nameInput, Integer startIndex, Integer maxResults, VehicleFields sortField, boolean isDistinctOrder);
    void deleteAllByFuelType(FuelType fuelType);
}
