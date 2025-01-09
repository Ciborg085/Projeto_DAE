package pt.ipleiria.estg.dei.ei.dae.backend.mappers;

import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.*;

import java.util.HashMap;

public class SensorMapper {
    public static SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setProperties(new HashMap<>());

        if (sensor instanceof GeoLocationSensor) {
            dto.setType("GeoLocationSensor");
            dto.getProperties().put("latitude", ((GeoLocationSensor) sensor).getLatitude());
            dto.getProperties().put("longitude", ((GeoLocationSensor) sensor).getLongitude());
        } else if (sensor instanceof PressureSensor) {
            dto.setType("PressureSensor");
            dto.getProperties().put("pressure", ((PressureSensor) sensor).getPressure());
        } else if (sensor instanceof TemperatureSensor) {
            dto.setType("TemperatureSensor");
            dto.getProperties().put("temperature", ((TemperatureSensor) sensor).getTemperature());
        } else if (sensor instanceof MultiSensor) {
            dto.setType("MultiSensor");
            dto.getProperties().put("latitude", ((GeoLocationSensor) sensor).getLatitude());
            dto.getProperties().put("longitude", ((GeoLocationSensor) sensor).getLongitude());
            dto.getProperties().put("pressure", ((PressureSensor) sensor).getPressure());
            dto.getProperties().put("temperature", ((TemperatureSensor) sensor).getTemperature());
        }

        return dto;
    }

    public static Sensor toEntity(SensorDTO dto) {
        Sensor sensor;
        switch (dto.getType()) {
            case "GeolocationSensor":
                GeoLocationSensor geoSensor = new GeoLocationSensor();
                geoSensor.setLatitude((Double) dto.getProperties().get("latitude"));
                geoSensor.setLongitude((Double) dto.getProperties().get("longitude"));
                sensor = geoSensor;
                break;
            case "PressureSensor":
                PressureSensor pressureSensor = new PressureSensor();
                pressureSensor.setPressure((Double) dto.getProperties().get("pressure"));
                sensor = pressureSensor;
                break;
            case "TemperatureSensor":
                TemperatureSensor temperatureSensor = new TemperatureSensor();
                temperatureSensor.setTemperature((Double) dto.getProperties().get("temperature"));
                sensor = temperatureSensor;
                break;
            case "MultiSensor":
                MultiSensor multiSensor = new MultiSensor();
                multiSensor.setLatitude((Double) dto.getProperties().get("latitude"));
                multiSensor.setLongitude((Double) dto.getProperties().get("longitude"));
                multiSensor.setPressure((Double) dto.getProperties().get("pressure"));
                multiSensor.setTemperature((Double) dto.getProperties().get("temperature"));
                sensor = multiSensor;
            default:
                throw new IllegalArgumentException("Unknown sensor type: " + dto.getType());
        }

        sensor.setId(dto.getId());
        return sensor;
    }
}

