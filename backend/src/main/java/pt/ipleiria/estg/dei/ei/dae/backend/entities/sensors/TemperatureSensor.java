package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;

@Entity
@DiscriminatorValue("TEMPERATURE")
public class TemperatureSensor extends Sensor{
    private Double temperature;

    public TemperatureSensor() {
    }

    public TemperatureSensor(long id, Volume volume) {
        super(id, volume);
        this.temperature = 0.0;
    }

    @Override
    public String GetAlertMessage() {
        return "Temperature sensor alert: Temperature too high! Temperature: "+this.temperature+"ºC";
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
