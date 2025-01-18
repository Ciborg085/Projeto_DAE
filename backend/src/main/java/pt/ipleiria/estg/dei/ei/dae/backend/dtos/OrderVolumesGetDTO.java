package pt.ipleiria.estg.dei.ei.dae.backend.dtos;


import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;

import java.util.List;

public class OrderVolumesGetDTO {
    private long order_id;
    private String client_name;
    private List<VolumeGetDTO> volumes;

    public OrderVolumesGetDTO() {
    }

    public OrderVolumesGetDTO(long order_id, String client_name, List<VolumeGetDTO> volumes) {
        this.order_id = order_id;
        this.client_name = client_name;
        this.volumes = volumes;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public List<VolumeGetDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeGetDTO> volumes) {
        this.volumes = volumes;
    }

}
