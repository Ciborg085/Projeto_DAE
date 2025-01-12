package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.SensorType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SensorPatchDTO {

    private Map<String, Object> properties;

    public SensorPatchDTO() { }

    public SensorPatchDTO(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }


    public static SensorPatchDTO from(Sensor sensor) {
        SensorPatchDTO dto = new SensorPatchDTO();
        dto.setProperties(new HashMap<>());

        if (sensor instanceof GeoLocationSensor) {
            dto.getProperties().put("latitude", ((GeoLocationSensor) sensor).getLatitude());
            dto.getProperties().put("longitude", ((GeoLocationSensor) sensor).getLongitude());
        } else if (sensor instanceof PressureSensor) {
            dto.getProperties().put("pressure", ((PressureSensor) sensor).getPressure());
        } else if (sensor instanceof TemperatureSensor) {
            dto.getProperties().put("temperature", ((TemperatureSensor) sensor).getTemperature());
        } else if (sensor instanceof MultiSensor) {
            dto.getProperties().put("latitude", ((GeoLocationSensor) sensor).getLatitude());
            dto.getProperties().put("longitude", ((GeoLocationSensor) sensor).getLongitude());
            dto.getProperties().put("pressure", ((PressureSensor) sensor).getPressure());
            dto.getProperties().put("temperature", ((TemperatureSensor) sensor).getTemperature());
        }
        return dto;
    }

    public static List<SensorPatchDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorPatchDTO::from).collect(Collectors.toList());
    }
}
