package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;

@Entity
@DiscriminatorValue("MULTI")
public class MultiSensor extends Sensor {

    private Double temperature;

    private Double pressure;

    private Double latitude;

    private Double longitude;

    public MultiSensor() {
    }

    public MultiSensor(long id, Volume volume) {
        super(id,volume);
        this.temperature = 0.0;
        this.pressure = 0.0;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    @Override
    public String GetAlertMessage() {
        return "Multi-sensor alert: Check all sensor readings for any abnormal conditions!";
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
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
