package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "sensor_type")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotNull
//    private String type;
//    private double temperature;
//    private double atmosphere_pressure;
//    private double acceleration;
//    private double latitude;
//    private double longitude;
    private String alert_message;

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

}
