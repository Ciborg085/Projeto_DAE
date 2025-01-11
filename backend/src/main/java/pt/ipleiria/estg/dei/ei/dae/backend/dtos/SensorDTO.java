package pt.ipleiria.estg.dei.ei.dae.backend.dtos;


import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    private long id;
    private String type;
    private Map<String, Object> properties;

    public SensorDTO() {}

    public SensorDTO(int id,String type) {
        this.id = id;
        this.type = type;
        this.properties = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }


    public static SensorDTO from(Sensor sensor) {
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

    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }

}

