package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.OrderStatus;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @NotNull
    private Client client;

    @NotNull
    private String destination;

    @NotNull
    private String payment_method;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus order_status = OrderStatus.CREATED;


    // Required
    public Order() {
    }

    public Order(long id, Client client, String destination, String payment_method, OrderStatus order_status) {
        this.id = id;
        this.client = client;
        this.destination = destination;
        this.payment_method = payment_method;
        this.order_status = order_status;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public OrderStatus getOrder_status() {
        return order_status;
    }

    public void setOrder_status(OrderStatus order_status) {
        this.order_status = order_status;
    }
}

