package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class OrderProduct {

    @Id
    @ManyToOne
    @JoinColumn(name = "orders", nullable = false)
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "users", nullable = false)
    private Usuario user;

    @Column(nullable = false)
    private int quantity;

    // Getters y Setters
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
