package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;

@Entity
@DiscriminatorValue("PRESSURE")
public class PressureSensor extends Sensor {
    private Double pressure;

    public PressureSensor() {
    }

    public PressureSensor(long id, Volume volume) {
        super(id, volume);
        this.pressure = 0.0;
    }

    @Override
    public String GetAlertMessage() {
        return "Pressure sensor alert: Pressure to high! value: "+ this.pressure;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}
