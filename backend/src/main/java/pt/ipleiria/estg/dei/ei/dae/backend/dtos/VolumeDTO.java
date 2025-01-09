package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.VolumeStatus;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {
    private int id;
    private Order order;
    private Sensor sensor;
    private Product product;
    private int quantity;
    private VolumeStatus volume_status = VolumeStatus.SENT ;

    public VolumeDTO() {}

    public VolumeDTO(int Id, Order order, Sensor sensor, int quantity, VolumeStatus volume_status) {
        this.id = Id;
        this.order = order;
        this.sensor = sensor;
        this.quantity = quantity;
        this.volume_status = volume_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public VolumeStatus getVolume_status() {
        return volume_status;
    }

    public void setVolume_status(VolumeStatus volume_status) {
        this.volume_status = volume_status;
    }

    public static VolumeDTO from (Volume volume){
        return new VolumeDTO(
                //volume.getId(),
                //volume.getOrder(),
                //volume.getSensor(),
                //volume.getQuantity(),
                //volume.getVolume_status()
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }
}
