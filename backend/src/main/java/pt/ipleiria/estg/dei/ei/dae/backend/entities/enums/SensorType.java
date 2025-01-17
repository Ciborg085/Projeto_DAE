package pt.ipleiria.estg.dei.ei.dae.backend.entities.enums;

import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.IllegalArgumentException;

public enum SensorType {
    GEOLOCATION("geolocationSensor"),
    TEMPERATURE("temperatureSensor"),
    PRESSURE("pressureSensor"),
    MULTI("multiSensor");

    private final String type;

    SensorType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public static SensorType fromString(String type) throws IllegalArgumentException {
        for (SensorType sensorType :  SensorType.values()) {
            if (sensorType.type.equals(type)) {
                return sensorType;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
