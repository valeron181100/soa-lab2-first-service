package valeron.bondar.services;

import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import valeron.bondar.database.FilterQueryBuilder;
import valeron.bondar.database.VehicleRepo;
import valeron.bondar.exceptions.ResponseException;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.VehicleFields;
import valeron.bondar.model.Vehicles;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchNameServiceImpl implements SearchNameService {

    private final VehicleRepo repo;

    @Override
    public Vehicles searchByName(String query, String startIndex, String maxResults, String sortField, String orderStr) throws ResponseException, UnsupportedEncodingException {
        if (query.isEmpty()) {
            throw new ResponseException(400, "filterNameParam have not to be empty");
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
                    vehicles = repo.findByName(query, Integer.parseInt(startIndex), Integer.parseInt(maxResults), VehicleFields.valueOf(sortField), isOrderDesc);
                } else if ((startIndex != null && !startIndex.isEmpty()))
                    vehicles = repo.findByName(query, Integer.parseInt(startIndex), null, VehicleFields.valueOf(sortField), isOrderDesc);
                else
                    vehicles = repo.findByName(query, null, Integer.parseInt(maxResults), VehicleFields.valueOf(sortField), isOrderDesc);
            } catch (IllegalArgumentException e) {
                throw new ResponseException(400, "Invalid query parameter");
            }
        } else
            try {
                vehicles = repo.findByName(query, VehicleFields.valueOf(sortField), isOrderDesc);
            } catch (IllegalArgumentException e) {
                throw new ResponseException(400, "Invalid query parameter");
            }

        Vehicles vehiclesXmlList = new Vehicles();
        vehiclesXmlList.setVehicle(vehicles);
        Long total = repo.totalCount();
        vehiclesXmlList.setTotalCount(vehiclesXmlList.getVehicle().size());
        return vehiclesXmlList;
    }
}
