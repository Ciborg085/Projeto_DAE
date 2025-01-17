package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

public class VolumeUpdateStatusDTO {

    private String status;

    public VolumeUpdateStatusDTO() {
    }

    public VolumeUpdateStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
