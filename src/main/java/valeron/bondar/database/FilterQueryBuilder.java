package valeron.bondar.database;

import valeron.bondar.model.VehicleFields;

import java.util.HashMap;
import java.util.Map;

public class FilterQueryBuilder {

    private HashMap<VehicleFields, String> fieldsMap;

    public FilterQueryBuilder() {
        fieldsMap = new HashMap<>();
    }

    public FilterQueryBuilder addFilter(VehicleFields field, String val) {
        fieldsMap.put(field, val);
        return this;
    }

    public String buildQuery() {
        StringBuilder res = new StringBuilder("where ");
        for (Map.Entry<VehicleFields, String> entry : fieldsMap.entrySet()) {
            if (entry.getKey().equals(VehicleFields.CREATION_DATE))
                continue;
            if (!entry.getValue().equals(""))
                if (entry.getKey() != VehicleFields.COORDINATES)
                    res.append(entry.getKey().toString().toLowerCase()).append("=").append("'").append(entry.getValue()).append("'").append(" and ");
                else {
                    int xCoord = Integer.parseInt(entry.getValue().replaceAll("[()]", "").split(",\\s")[0]);
                    int yCoord = Integer.parseInt(entry.getValue().replaceAll("[()]", "").split(",\\s")[1]);
                    res
                            .append(entry.getKey().toString().toLowerCase()).append(".x")
                            .append("=").append("'").append(xCoord).append("'").append(" and ")
                            .append(entry.getKey().toString().toLowerCase()).append(".y")
                            .append("=").append("'").append(yCoord).append("'").append(" and ");
                }
        }
        String resStr = res.toString();
        return resStr.substring(0, resStr.length() - 4);
    }

}
