package valeron.bondar.services;

import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.Vehicles;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface VehicleService {
    Vehicles getVehicles(String startIndex, String maxResults, String sortField, String orderStr, String filtersParam) throws ResponseException, UnsupportedEncodingException;
    Vehicle createVehicle(Vehicle vehicle) throws ResponseException;
    Vehicle removeVehicle(Integer id) throws ResponseException;
    Vehicle updateVehicle(Integer id, Vehicle nVehicle) throws ResponseException;
    Vehicle getVehicleById(Integer id) throws ResponseException;
    void generateTestData();
}
