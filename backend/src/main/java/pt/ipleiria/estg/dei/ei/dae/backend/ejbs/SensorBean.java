package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.SensorType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.*;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.IllegalArgumentException;
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

    public void create(long id, long volume_id, String type) throws MyEntityNotFoundException, MyEntityExistsException, IllegalArgumentException {
        if (exists(id)) {
            throw new MyEntityExistsException("Sensor "+id+" already exists.");
        }

        Volume volume = volumeBean.find(volume_id);
        SensorType sensorType = SensorType.fromString(type);

        switch (sensorType) {
            case GEOLOCATION:
                GeoLocationSensor geoLocationSensor = new GeoLocationSensor(id,volume);
                entityManager.persist(geoLocationSensor);
                volume.addSensor(geoLocationSensor);
                break;
            case PRESSURE:
                PressureSensor pressureSensor = new PressureSensor(id, volume);
                entityManager.persist(pressureSensor);
                volume.addSensor(pressureSensor);
                break;
            case TEMPERATURE:
                TemperatureSensor temperatureSensor = new TemperatureSensor(id, volume);
                entityManager.persist(temperatureSensor);
                volume.addSensor(temperatureSensor);
                break;
            case MULTI:
                MultiSensor multiSensor = new MultiSensor(id, volume);
                entityManager.persist(multiSensor);
                volume.addSensor(multiSensor);
                break;
            default:
                System.out.println(sensorType);
                throw new RuntimeException("Invalid sensor type");
        }
    }
    public Sensor find(long id) throws MyEntityNotFoundException {
        Sensor sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor "+id+" not found");
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
        if (!exists(id)) {
            throw new MyEntityNotFoundException("Sensor "+id+" not found.");
        }

        Sensor sensor = entityManager.find(Sensor.class,id);
        entityManager.lock(sensor, LockModeType.OPTIMISTIC);

        if (sensor instanceof GeoLocationSensor) {
            if (properties.containsKey("latitude") && properties.containsKey("longitude")) {
                ((GeoLocationSensor) sensor).setLatitude((Double) properties.get("latitude"));
                ((GeoLocationSensor) sensor).setLongitude((Double) properties.get("longitude"));
            }
        } else if (sensor instanceof PressureSensor) {
            if (properties.containsKey("pressure")) {
                ((PressureSensor) sensor).setPressure((Double) properties.get("pressure"));
            }
        } else if (sensor instanceof TemperatureSensor) {
            if (properties.containsKey("temperature")) {
                ((TemperatureSensor) sensor).setTemperature((Double) properties.get("temperature"));
            }
        } else if (sensor instanceof MultiSensor) {
            if (properties.containsKey("latitude") && properties.containsKey("longitude")) {
                ((MultiSensor) sensor).setLatitude((Double) properties.get("latitude"));
                ((MultiSensor) sensor).setLongitude((Double) properties.get("longitude"));
            }
            if (properties.containsKey("pressure")) {
                ((MultiSensor) sensor).setPressure((Double) properties.get("pressure"));
            }
            if (properties.containsKey("temperature")) {
                ((MultiSensor) sensor).setTemperature((Double) properties.get("temperature"));
            }
        } else {
            throw new RuntimeException("Sensor "+sensor.getId()+" is of a invalid type");
        }

        entityManager.merge(sensor);
    }


}
