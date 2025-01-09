package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class VolumeBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    //TODO missing error handling
    public void create(long volume_id, long order_id, long product_id, int quantity)
            throws MyEntityNotFoundException {
        Order order = orderBean.find(order_id);
        if (order == null) {
            throw new MyEntityNotFoundException("order "+order_id+" not found");
        }

        Product product = productBean.find(product_id);
        if (product == null) {
            throw new MyEntityNotFoundException("product "+product_id+" not found");
        }

        Volume volume = new Volume(volume_id,order,product,quantity);
        entityManager.persist(volume);
    }

    //TODO
    public void update() {
    }

    public List<Volume> findAll() {
       return new ArrayList<>();
    }

    public Volume find(long id)
            throws MyEntityNotFoundException {
       Volume volume = entityManager.find(Volume.class, id);
       if (volume == null) {
           throw new MyEntityNotFoundException("volume " + id + " not found");
       }

       return volume;
    }

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(v.id) FROM Volume v WHERE v.id = :id",
                Long.class
        );
        query.setParameter("id",id);
        return (Long)query.getSingleResult() > 0L;
    }
}
