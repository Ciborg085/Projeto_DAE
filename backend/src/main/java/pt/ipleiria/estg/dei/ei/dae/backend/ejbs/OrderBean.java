package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.OrderStatus;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class OrderBean {
    //TODO
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    public void create(long id,String client_username, String destination)
            throws MyEntityNotFoundException {
        Client client = clientBean.find(client_username);
        Order order = new Order(id,client,destination, OrderStatus.CREATED);
        entityManager.persist(order);
    }

    public Order find(long id) throws MyEntityNotFoundException {
        Order order = entityManager.find(Order.class, id);
        if (order == null)  {
            throw new MyEntityNotFoundException("Order " + id + " not found");
        }
        return order;
    }

    public List<Order> findAll() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

}
