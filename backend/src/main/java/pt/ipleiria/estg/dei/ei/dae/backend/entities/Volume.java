package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.Sensor;

import java.util.List;

@Entity
public class Volume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToMany
    @JoinColumn(name = "sensor_id", nullable = false)
    private List<Sensor> sensor;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    private Status volume_status = Status.Sent ;

    public enum Status {
        Sent,
        Aborted,
        Delivered
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

    public Status getVolume_status() {
        return volume_status;
    }

    public void setVolume_status(Status volume_status) {
        this.volume_status = volume_status;
    }
}

