package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
/*
    Preciso de pensar melhor como fazer isto.
    Que dados vou guardar, que dados preciso no request, e que dados posso importar da base de dados.
 */
public class VolumeCreatePostDTO implements Serializable {
    // Order_id or Order, only one can be present at a time
    private long volume_id;
    private Optional<Long> order_id;
    private Optional<OrderForVolumeDTO> order;
    private long product_id;
    private int quantity;
    private List<SensorForVolumeDTO> sensors;

    public VolumeCreatePostDTO() {
        this.sensors = new ArrayList<SensorForVolumeDTO>();
    }

    public VolumeCreatePostDTO(long volume_id, Optional<Long> order_id, Optional<OrderForVolumeDTO> order, long product_id, int quantity, List<SensorForVolumeDTO> sensors) {
        this.volume_id = volume_id;
        this.order_id = order_id;
        this.order = order;
        this.product_id = product_id;
        this.quantity = quantity;
        this.sensors = sensors;
        this.sensors = new ArrayList<SensorForVolumeDTO>();
    }

    public long getVolume_id() {
        return volume_id;
    }

    public void setVolume_id(long volume_id) {
        this.volume_id = volume_id;
    }

    public Optional<Long> getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Optional<Long> order_id) {
        this.order_id = order_id;
    }

    public Optional<OrderForVolumeDTO> getOrder() {
        return order;
    }

    public void setOrder(Optional<OrderForVolumeDTO> order) {
        this.order = order;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<SensorForVolumeDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorForVolumeDTO> sensors) {
        this.sensors = sensors;
    }

}
