package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.VolumeStatus;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeGetDTO {
    private long id;
    private long order_id;
    private List<SensorDTO> sensors;
    private ProductSummaryDTO product;
    private int quantity;
    private VolumeStatus volume_status = VolumeStatus.SENT ;

    public VolumeGetDTO() {}

    public VolumeGetDTO(long id, long order_id, List<SensorDTO> sensors, ProductSummaryDTO product, int quantity, VolumeStatus volume_status) {
        this.id = id;
        this.order_id = order_id;
        this.sensors = sensors;
        this.product = product;
        this.quantity = quantity;
        this.volume_status = volume_status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public List<SensorDTO> getSensor() {
        return sensors;
    }

    public void setSensor(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }

    public ProductSummaryDTO getProduct() {
        return product;
    }

    public void setProduct(ProductSummaryDTO product) {
        this.product = product;
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

     //public static VolumeGetDTO from (Volume volume){
     //    return new VolumeGetDTO(
     //            volume.getId(),
     //            volume.getOrder(),
     //            volume.getSensor(),
     //            volume.getQuantity(),
     //            volume.getVolume_status()
     //    );
     //}

     //public static List<VolumeGetDTO> from(List<Volume> volumes) {
     //    return volumes.stream().map(VolumeGetDTO::from).collect(Collectors.toList());
     //}
}
