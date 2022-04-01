package valeron.bondar.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;
import valeron.bondar.model.FuelType;
import valeron.bondar.model.Vehicle;
import valeron.bondar.model.VehicleFields;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomVehicleRepoImpl implements CustomVehicleRepo {

    @PersistenceContext
    private EntityManager session;

    @Override
    public List<Vehicle> findAll(VehicleFields sortField, boolean isDistinctOrder, String filters) {
        if (filters == null)
            filters = "";
        String orderStr = isDistinctOrder ? " desc " : " ";
        List<Vehicle> vehicles = (List<Vehicle>) session.createQuery("From Vehicle " + filters + " order by " + sortField.getFieldName() + orderStr).getResultList();
        session.close();
        return vehicles;
    }

    @Override
    public List<Vehicle> findAll(Integer startIndex, Integer maxResults, VehicleFields sortField, boolean isDistinctOrder, String filters) {
        if (filters == null)
            filters = "";
        String orderStr = isDistinctOrder ? " desc" : "";
        String queryString = "select v from Vehicle v " + filters + " order by " + sortField.getFieldName() + orderStr;
        Query query= session.createQuery(queryString);
        if (startIndex != null)
            query.setFirstResult(startIndex);
        if (maxResults != null)
            query.setMaxResults(maxResults);
        List<Vehicle> vehicles = (List<Vehicle>) query.getResultList();
        session.close();
        return vehicles;
    }

    @Override
    public Long totalCount() {
        Query query= session.createQuery("select count(id) from Vehicle");
        Long result = (Long)query.getSingleResult();
        session.close();
        return result;
    }

    @Override
    public double countAvgNumberOfWheels() {
        Query query = session.createQuery("select avg(numberOfWheels) from Vehicle");
        double result = (double)query.getSingleResult();
        session.close();
        return result;
    }

    @Override
    public List<Vehicle> findByName(String nameInput, VehicleFields sortField, boolean isDistinctOrder) {
        String orderStr = isDistinctOrder ? " desc" : "";
        List<Vehicle> vehicles = (List<Vehicle>) session.createQuery("From Vehicle where name like '" + nameInput + "%'" + " order by " + sortField.getFieldName() + orderStr).getResultList();
        session.close();
        return vehicles;
    }

    @Override
    public List<Vehicle> findByName(String nameInput, Integer startIndex, Integer maxResults, VehicleFields sortField, boolean isDistinctOrder) {
        String orderStr = isDistinctOrder ? " desc" : "";
        String queryString = "From Vehicle where name like '" + nameInput + "%'" + " order by " + sortField.getFieldName() + orderStr;
        Query query = session.createQuery(queryString);
        if (startIndex != null)
            query.setFirstResult(startIndex);
        if (maxResults != null)
            query.setMaxResults(maxResults);
        List<Vehicle> vehicles = (List<Vehicle>) query.getResultList();
        session.close();
        return vehicles;
    }

    @Override
    @Transactional
    public void deleteAllByFuelType(FuelType fuelType) {
        Query query = session.createQuery("delete from Vehicle where fuelType = " + fuelType.ordinal());
        query.executeUpdate();
        session.close();
    }
}
