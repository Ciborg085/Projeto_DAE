package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.Entity;

@Entity
public class PressureSensor extends Sensor {
    private Double pressure;

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}
