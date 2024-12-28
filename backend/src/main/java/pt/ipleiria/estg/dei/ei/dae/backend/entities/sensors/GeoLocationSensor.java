package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.Entity;

@Entity
public class GeoLocationSensor extends Sensor{
    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
