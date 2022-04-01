package valeron.bondar.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import valeron.bondar.model.FuelType;
import valeron.bondar.model.Vehicle;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Integer>, CustomVehicleRepo {

}
