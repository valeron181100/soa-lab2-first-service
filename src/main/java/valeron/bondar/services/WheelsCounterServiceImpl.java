package valeron.bondar.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import valeron.bondar.database.VehicleRepo;

@Service
@AllArgsConstructor
public class WheelsCounterServiceImpl implements WheelsCounterService {

    private final VehicleRepo repo;

    @Override
    public double countAvgWheels() {
        return repo.countAvgNumberOfWheels();
    }
}
