package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.Entity;

@Entity
public class TemperatureSensor extends Sensor{
    private Double temperature;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
