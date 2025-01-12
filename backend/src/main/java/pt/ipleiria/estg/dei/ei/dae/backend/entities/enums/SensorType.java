package pt.ipleiria.estg.dei.ei.dae.backend.entities.enums;

// TODO use this, make them = to strings
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
}
