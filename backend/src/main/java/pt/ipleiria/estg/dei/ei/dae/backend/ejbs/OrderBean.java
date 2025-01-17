package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.OrderStatus;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.IllegalArgumentException;

import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    public void create(long id,String client_username, String destination)
            throws MyEntityNotFoundException, MyEntityExistsException {
        if (exists(id)) {
            throw new MyEntityExistsException("Order already exists");
        }
        Client client = clientBean.find(client_username);
        Order order = new Order(id,client,destination, OrderStatus.CREATED);
        entityManager.persist(order);
    }

    public Order find(long id) throws MyEntityNotFoundException {
        Order order = entityManager.find(Order.class, id);
        if (order == null)  {
            throw new MyEntityNotFoundException("OrderBean::find: Order " + id + " not found");
        }
        return order;
    }
    public Order findWithProducts(long id) throws MyEntityNotFoundException {
        Order order = this.find(id);
        Hibernate.initialize(order.getProducts());
        return order;
    }

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(o.id) FROM Order o WHERE o.id = :id",
                Long.class
        );
        query.setParameter("id",id);
        return (Long)query.getSingleResult() > 0L;
    }

    public List<Order> findAll() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public List<Order> findAllWithProducts() {
        return entityManager.createNamedQuery("getAllOrdersWithProducts", Order.class).getResultList();
    }

    public void updateStatus(long id, String status) throws MyEntityNotFoundException, IllegalArgumentException {
       Order order = this.find(id);

       OrderStatus orderStatus = OrderStatus.fromString(status);
       System.out.println("getorder_status():  \'"+order.getOrder_status()+"\'; orderStatus:\'"+orderStatus+"\'");

       if (order.getOrder_status() == orderStatus) {
           throw new IllegalArgumentException("Already " + status);
       }

       order.setOrder_status(orderStatus);
       entityManager.merge(order);
    }

}
