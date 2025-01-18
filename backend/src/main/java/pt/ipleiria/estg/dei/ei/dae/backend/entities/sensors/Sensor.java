package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.SensorType;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensors",
                query = "SELECT s FROM Sensor s ORDER BY s.id"
        ),
        @NamedQuery(
                name = "getAllSensorsWhereUsername",
                query = "SELECT s FROM Sensor s WHERE s.volume.order.client.username = :client_username ORDER BY s.id"
        )
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "sensor_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "sensors")
public abstract class Sensor {

    @Id
    private long id;

    @Version
    private int version;

    @ManyToOne
    @NotNull
    private Volume volume;

    public Sensor() {
    }

    public Sensor(long id, Volume volume) {
        this.id = id;
        this.volume = volume;
    }

    public abstract String GetAlertMessage();

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
