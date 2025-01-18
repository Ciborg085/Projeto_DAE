package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.enums.VolumeStatus;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.IllegalArgumentException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
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
            throws MyEntityNotFoundException, MyEntityExistsException {
        if (exists(volume_id)) {
            throw new MyEntityExistsException("Volume "+volume_id+" already exists.");
        }

        System.out.println("Order_id: "+order_id);
        Order order = orderBean.find(order_id);
        if (order == null) {
            throw new MyEntityNotFoundException("VolumeBean::create: order "+order_id+" not found");
        }

        Product product = productBean.find(product_id);
        if (product == null) {
            throw new MyEntityNotFoundException("Volume::create: product "+product_id+" not found");
        }

        Volume volume = new Volume(volume_id,order,product,quantity);
        entityManager.persist(volume);
        order.addVolume(volume);
        entityManager.merge(order);
        entityManager.flush();
    }

    //TODO do something when delivered
    public void updateStatus(long id, String status) throws MyEntityNotFoundException, IllegalArgumentException {
        Volume volume = this.find(id);

        VolumeStatus volumeStatus = VolumeStatus.fromString(status);

        if (volume.getVolume_status() == volumeStatus) {
             throw new IllegalArgumentException("Already "+ volumeStatus);
        }

        volume.setVolume_status(volumeStatus);
        entityManager.merge(volume);
    }


    public List<Volume> findAll() {
       return new ArrayList<>();
    }

    // TODO use hibernate.initialize instead of fetch
    // TODO need to check if this ^^^ is even better then just fetch
    public List<Volume> findAllComplete() {
        return entityManager.createNamedQuery("getAllVolumeComplete", Volume.class).getResultList();
    }

    public Volume findComplete(long id) throws MyEntityNotFoundException {
        Volume volume = this.find(id);
        Hibernate.initialize(volume.getProduct());
        Hibernate.initialize(volume.getSensors());
        return volume;
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
