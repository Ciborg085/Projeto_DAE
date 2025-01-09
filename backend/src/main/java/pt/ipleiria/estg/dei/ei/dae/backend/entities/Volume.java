package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.VolumeStatus;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "volume")
public class Volume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToMany(mappedBy = "volume")
    private List<Sensor> sensors;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    private int quantity;

    @Version
    private int version;

    private VolumeStatus volume_status;

    public Volume() {
        this.sensors = new LinkedList<Sensor>();
    }

    public Volume(long id, Order order, Product product, int quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.volume_status = VolumeStatus.SENT;
        this.sensors = new LinkedList<Sensor>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensor) {
        this.sensors = sensor;
    }

    public void addSensor(Sensor sensor) throws MyEntityExistsException {
        if (this.sensors.contains(sensor)) {
            throw new MyEntityExistsException("Already contains this sensor");
        }
        this.sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) throws MyEntityNotFoundException {
         if (!this.sensors.contains(sensor)) {
             throw new MyEntityNotFoundException("Volume does not contain this sensor");
         }
         this.sensors.remove(sensor);
    }
}

