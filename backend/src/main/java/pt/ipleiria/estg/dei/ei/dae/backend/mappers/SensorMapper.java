package pt.ipleiria.estg.dei.ei.dae.backend.mappers;

import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.SensorType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.*;

import java.util.HashMap;
import java.util.Map;

public class SensorMapper {
    /*
    TODO
    This map is not used everywhere, need to consider if it's worth having this extra class or integrating it in a DTO
     */
    public static SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setProperties(new HashMap<>());

        if (sensor instanceof GeoLocationSensor) {
            dto.setType(SensorType.GEOLOCATION.getType());
            dto.getProperties().put("latitude", ((GeoLocationSensor) sensor).getLatitude());
            dto.getProperties().put("longitude", ((GeoLocationSensor) sensor).getLongitude());
        } else if (sensor instanceof PressureSensor) {
            dto.setType(SensorType.PRESSURE.getType());
            dto.getProperties().put("pressure", ((PressureSensor) sensor).getPressure());
        } else if (sensor instanceof TemperatureSensor) {
            dto.setType(SensorType.TEMPERATURE.getType());
            dto.getProperties().put("temperature", ((TemperatureSensor) sensor).getTemperature());
        } else if (sensor instanceof MultiSensor) {
            dto.setType(SensorType.MULTI.getType());
            dto.getProperties().put("latitude", ((GeoLocationSensor) sensor).getLatitude());
            dto.getProperties().put("longitude", ((GeoLocationSensor) sensor).getLongitude());
            dto.getProperties().put("pressure", ((PressureSensor) sensor).getPressure());
            dto.getProperties().put("temperature", ((TemperatureSensor) sensor).getTemperature());
        }

        return dto;
    }

    public static Sensor toEntity(SensorDTO dto) {
        /*
         I have to do this, so I can use the enum inside the switch
         If this is a bottleneck we can consider modifying it
         We can use an if chain ,but I think this is preferable.
         */
        Map<String, SensorType> typeToSensorTypeMap = new HashMap<>();
        for (SensorType sensorType : SensorType.values()) {
            typeToSensorTypeMap.put(sensorType.getType(), sensorType);
        }

        Sensor sensor;
        SensorType sensorType = typeToSensorTypeMap.get(dto.getType());
        switch (sensorType) {
            case GEOLOCATION:
                GeoLocationSensor geoSensor = new GeoLocationSensor();
                geoSensor.setLatitude((Double) dto.getProperties().get("latitude"));
                geoSensor.setLongitude((Double) dto.getProperties().get("longitude"));
                sensor = geoSensor;
                break;
            case PRESSURE:
                PressureSensor pressureSensor = new PressureSensor();
                pressureSensor.setPressure((Double) dto.getProperties().get("pressure"));
                sensor = pressureSensor;
                break;
            case TEMPERATURE:
                TemperatureSensor temperatureSensor = new TemperatureSensor();
                temperatureSensor.setTemperature((Double) dto.getProperties().get("temperature"));
                sensor = temperatureSensor;
                break;
            case MULTI:
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

