package valeron.bondar.services;

import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import valeron.bondar.database.FilterQueryBuilder;
import valeron.bondar.database.VehicleRepo;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.*;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepo repo;

    public Vehicles getVehicles(String startIndex, String maxResults, String sortField, String orderStr, String filtersParam) throws ResponseException, UnsupportedEncodingException {
        String filters = null;
        if (filtersParam != null) {
            filtersParam = URLDecoder.decode(filtersParam, StandardCharsets.UTF_8.name());
            if (!filtersParam.equals("{}")) {

                JSONObject filtersObj = null;

                try {
                    filtersObj = new JSONObject(filtersParam);
                } catch (JSONException e) {
                    throw new ResponseException(400, "Invalid JSON filters");
                }

                FilterQueryBuilder builder = new FilterQueryBuilder();
                for (int i = 0; i < filtersObj.keySet().toArray().length; i++) {
                    String key = (String) filtersObj.keySet().toArray()[i];
                    VehicleFields field = VehicleFields.fromFieldName(key);
                    if (field == null) {
                        throw new ResponseException(400, "Filters url has no field with name " + key);
                    }
                    builder.addFilter(field, filtersObj.getString(field.getFieldName()));
                }
                filters = builder.buildQuery();
            }
        }

        boolean isOrderDesc = false;
        if (orderStr != null)
            isOrderDesc = true;
        if (sortField == null)
            sortField = "CREATION_DATE";

        List<Vehicle> vehicles = null;
        if ((startIndex != null && !startIndex.isEmpty()) || (maxResults != null && !maxResults.isEmpty())) {
            try {
                if ((startIndex != null && !startIndex.isEmpty()) && (maxResults != null && !maxResults.isEmpty())) {
                    vehicles = repo.findAll(Integer.parseInt(startIndex), Integer.parseInt(maxResults), VehicleFields.valueOf(sortField), isOrderDesc, filters);
                } else if ((startIndex != null && !startIndex.isEmpty()))
                    vehicles = repo.findAll(Integer.parseInt(startIndex), null, VehicleFields.valueOf(sortField), isOrderDesc, filters);
                else
                    vehicles = repo.findAll(null, Integer.parseInt(maxResults), VehicleFields.valueOf(sortField), isOrderDesc, filters);
            } catch (IllegalArgumentException e) {
                throw new ResponseException(400, "Invalid query parameter");
            }
        } else
            try {
                vehicles = repo.findAll(VehicleFields.valueOf(sortField), isOrderDesc, filters);
            } catch (IllegalArgumentException e) {
                throw new ResponseException(400, "Invalid query parameter");
            }

        Vehicles vehiclesXmlList = new Vehicles();
        vehiclesXmlList.setVehicle(vehicles);
        Long total = repo.totalCount();
        vehiclesXmlList.setTotalCount(total.intValue());
        return vehiclesXmlList;
//        return XMLConverter.convert(vehiclesXmlList);
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) throws ResponseException {
        try {
            if (vehicle.getCoordinates() == null || vehicle.getCreationDate() == null || vehicle.getName() == null) {
                throw new ResponseException(400, "All fields except fuelType and vehicleType must not be empty");
            }
            repo.save(vehicle);
        } catch (ConstraintViolationException e) {
            throw new ResponseException(400, "The data does not meet the database constraints");
        }
        return vehicle;
    }

    @Override
    public Vehicle removeVehicle(Integer id) throws ResponseException {
        Vehicle dbVehicle = repo.findById(id).orElse(null);
        if (dbVehicle == null) {
            throw new ResponseException(400, "Not found vehicle");
        }
        repo.deleteById(id);
        return dbVehicle;
    }

    @Override
    public Vehicle updateVehicle(Integer id, Vehicle nVehicle) throws ResponseException {
        Vehicle dbVehicle = repo.findById(id).orElse(null);
        if (dbVehicle == null) {
            throw new ResponseException(400, "Can't update non-existent vehicle");
        }
        nVehicle.setId(dbVehicle.getId());
        repo.save(nVehicle);
        return nVehicle;
    }

    @Override
    public Vehicle getVehicleById(Integer id) throws ResponseException {
        Vehicle vehicle = repo.findById(id).orElse(null);
        if (vehicle == null) {
            throw new ResponseException(400, "Vehicle with provided Id not found");
        }
        return vehicle;
    }

    @Override
    public void generateTestData() {
        Coordinates coordinates = new Coordinates();
        coordinates.setxCoord(1);
        coordinates.setyCoord(2);
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Kia K5");
        vehicle.setCoordinates(coordinates);
        vehicle.setEnginePower(5);
        vehicle.setFuelType(FuelType.DIESEL);
        vehicle.setNumberOfWheels(4);
        vehicle.setType(VehicleType.SEDAN);
        repo.save(vehicle);
        vehicle.setName("Hyundai Solaris");
        vehicle.setFuelType(FuelType.KEROSENE);
        repo.save(vehicle);
        vehicle.setName("Mercedes Benz");
        vehicle.setFuelType(FuelType.MANPOWER);
        repo.save(vehicle);
    }
}
