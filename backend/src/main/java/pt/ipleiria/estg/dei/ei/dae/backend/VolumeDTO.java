package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {
    private int Id;
    private Order order;
    private Sensor sensor;
    private Product product;
    private int quantity;
    private Volume.Status volume_status = Volume.Status.Sent ;

    public enum Status {
        Sent,
        Aborted,
        Delivered
    }
    public VolumeDTO() {}

    public VolumeDTO(int Id, Order order, Sensor sensor, int quantity, Volume.Status volume_status) {
        this.Id = Id;
        this.order = order;
        this.sensor = sensor;
        this.quantity = quantity;
        this.volume_status = volume_status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
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

    public Volume.Status getVolume_status() {
        return volume_status;
    }

    public void setVolume_status(Volume.Status volume_status) {
        this.volume_status = volume_status;
    }

    public static VolumeDTO from (Volume volume){
        return new VolumeDTO(
                volume.getId(),
                volume.getOrder(),
                volume.getSensor(),
                volume.getQuantity(),
                volume.getVolume_status()
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }
}
