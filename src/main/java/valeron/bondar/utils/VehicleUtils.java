package valeron.bondar.utils;

import org.hibernate.Session;
import valeron.bondar.model.VehicleFields;

public class VehicleUtils {
    public static String getFindAllVehiclesString(Integer startIndex, Integer maxResults, VehicleFields sortField, boolean isDistinctOrder, String filters) {
        if (filters == null)
            filters = "";
        String orderStr = isDistinctOrder ? " desc" : "";

        String limits = "";
        if (startIndex != null)
            limits += " OFFSET " + startIndex + " ";
        if (maxResults != null) {
            limits += " LIMIT " + maxResults + " ";
        }

        return "select * from vehicle" + limits + filters + " order by " + sortField.getFieldName() + orderStr;
    }
}
