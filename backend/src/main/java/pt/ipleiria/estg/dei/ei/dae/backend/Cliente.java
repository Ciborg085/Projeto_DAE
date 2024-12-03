package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.criteria.Order;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{
    public List<Order> encomendas;

    public Cliente() {
        super();
        encomendas = new ArrayList<>();
    }
    public List<Order> getEncomendas() {
        return encomendas;
    }
    public void addOrder(Order order){
        encomendas.add(order);
    }


}
