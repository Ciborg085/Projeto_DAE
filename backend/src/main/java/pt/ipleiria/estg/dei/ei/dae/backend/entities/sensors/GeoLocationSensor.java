package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;

@Entity
@DiscriminatorValue("GEOLOCATION")
public class GeoLocationSensor extends Sensor{
    private Double latitude;
    private Double longitude;

    public GeoLocationSensor() {
    }

    public GeoLocationSensor(long id, Volume volume) {
        super(id, volume);
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    @Override
    public String GetAlertMessage() {
        throw new UnsupportedOperationException("Geolocation does not have an alert message");
    }

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
