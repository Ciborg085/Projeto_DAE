package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.OrderStatus;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.id"
        ),
        @NamedQuery(
                name = "getAllOrdersWithProducts",
                query = "SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.products ORDER BY o.id"
        ),
        @NamedQuery(
                name = "getAllOrdersWithProductsWhereUsername",
                query = "SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.products WHERE o.client.username = :client_username ORDER BY o.id "
        ),
})
@Table(name = "orders")
public class Order {

    @Id
    private long id;

    @ManyToOne
    @NotNull
    private Client client;

    @NotBlank
    private String destination;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
    private List<Product> products;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<Volume> volumes;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus order_status = OrderStatus.CREATED;

    @Version
    private int version;


    // Required
    public Order() {
        this.products = new ArrayList<Product>();
        this.volumes = new ArrayList<Volume>();
    }

    public Order(long id, Client client, String destination, OrderStatus order_status) {
        this.id = id;
        this.client = client;
        this.destination = destination;
        this.order_status = order_status;
        this.products = new ArrayList<Product>();
        this.volumes = new ArrayList<Volume>();
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

    public OrderStatus getOrder_status() {
        return order_status;
    }

    public void setOrder_status(OrderStatus order_status) {
        this.order_status = order_status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public void addProduct(Product product) throws MyEntityExistsException {
        if (this.products.contains(product)) {
            throw new MyEntityExistsException("Product already in the list");
        }
        this.products.add(product);
    }

    public void removeProduct(Product product) throws MyEntityNotFoundException {
        if (!this.products.contains(product)) {
            throw new MyEntityNotFoundException("Product not in the list");
        }
        this.products.remove(product);
    }

    public void addVolume(Volume volume) throws MyEntityExistsException {
        if (this.volumes.contains(volume)) {
            throw new MyEntityExistsException("Volume already in the list");
        }
        this.volumes.add(volume);
    }

    public void removeVolume(Volume volume) throws MyEntityNotFoundException {
        if (!this.volumes.contains(volume)) {
            throw new MyEntityNotFoundException("Volume not in the list");
        }
        this.volumes.remove(volume);
    }


}

