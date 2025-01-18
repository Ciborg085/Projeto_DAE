package pt.ipleiria.estg.dei.ei.dae.backend.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name="getAllClients",
                query="SELECT c FROM Client c ORDER BY c.name"
        )
})
@DiscriminatorValue("Client")
public class Client extends User{

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client() { this.orders = new LinkedList<>(); }

    public Client(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.orders = new LinkedList<Order>();
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void addOrder(Order order){
        orders.add(order);
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
