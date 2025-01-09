package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.io.Serializable;

public class SensorForVolumeDTO implements Serializable {
    private long id;
    private String type;

    public SensorForVolumeDTO() {
    }

    public SensorForVolumeDTO(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
