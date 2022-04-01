package valeron.bondar.services;

import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.FuelType;

public interface DeleteFuelService {
    void deleteByFuelType(String fuelType) throws ResponseException;
}
