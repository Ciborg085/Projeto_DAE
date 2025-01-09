package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.*;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.Map;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private VolumeBean volumeBean;

    public void create(long id, long volume_id, String type) throws MyEntityNotFoundException, MyEntityExistsException {
        Volume volume = volumeBean.find(volume_id);

        switch (type) {
            case "GeoLocationSensor":
                GeoLocationSensor geoLocationSensor = new GeoLocationSensor(id,volume);
                entityManager.persist(geoLocationSensor);
                volume.addSensor(geoLocationSensor);
                break;
            case  "PressureSensor":
                PressureSensor pressureSensor = new PressureSensor(id, volume);
                entityManager.persist(pressureSensor);
                volume.addSensor(pressureSensor);
                break;
            case "TemperatureSensor":
                TemperatureSensor temperatureSensor = new TemperatureSensor(id, volume);
                entityManager.persist(temperatureSensor);
                volume.addSensor(temperatureSensor);
                break;
            case "MultiSensor":
                MultiSensor multiSensor = new MultiSensor(id, volume);
                entityManager.persist(multiSensor);
                volume.addSensor(multiSensor);
                break;
            default:
                throw new RuntimeException("Invalid sensor type");
        }
    }
    public Sensor find(long id) throws MyEntityNotFoundException {
        Sensor sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("sensor "+id+" not found");
        }
        return sensor;
    }

    public List<Sensor> findAll() throws MyEntityNotFoundException {
        return entityManager.createNamedQuery("getAllSensors",Sensor.class).getResultList();
    }

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
               "SELECT COUNT(s.id) FROM Sensor s WHERE s.id = :id",
                Long.class
        );
        query.setParameter("id",id);
        return (Long)query.getSingleResult() > 0L;
    }

    public void updateValues(long id, Map<String,Object> properties) throws MyEntityNotFoundException{
        Sensor sensor = entityManager.find(Sensor.class,id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("sensor "+id+" not found");
        }
        entityManager.lock(sensor, LockModeType.OPTIMISTIC);
        // TODO logic to update values dynamically
    }


}
