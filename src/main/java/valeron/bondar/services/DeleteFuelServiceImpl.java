package valeron.bondar.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import valeron.bondar.database.VehicleRepo;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.FuelType;

@Service
@AllArgsConstructor
public class DeleteFuelServiceImpl implements DeleteFuelService {

    private final VehicleRepo repo;

    @Override
    public void deleteByFuelType(String fuelType) throws ResponseException {
        if (fuelType.isEmpty()) {
            throw new ResponseException(400, "Query parameter was expected");
        }
        FuelType fuelTypeObj = FuelType.valueOf(fuelType);
        repo.deleteAllByFuelType(fuelTypeObj);
    }
}
