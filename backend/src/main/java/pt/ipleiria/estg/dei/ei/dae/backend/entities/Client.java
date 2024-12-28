package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User{

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client() { this.orders = new ArrayList<>(); }

    public Client(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.orders = new ArrayList<>();
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void addOrder(Order order){
        orders.add(order);
    }


}
