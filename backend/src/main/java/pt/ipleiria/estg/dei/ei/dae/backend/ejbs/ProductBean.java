package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.ApplicationException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String name, String brand, float price, int maxQuantityPerVolume, String typeOfPackage)
            throws MyEntityExistsException {
        if(exists(name,brand)) {
            throw new MyEntityExistsException(
                    "Product with name '" + name + "' and brand '" + brand + "' already exists" );
        }

        var product = new Product(name, brand, price, maxQuantityPerVolume, typeOfPackage);
        entityManager.persist(product);
    }

    public boolean exists(String name, String brand) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.name) FROM Product p WHERE p.name = :name AND p.brand = :brand",
                Long.class
        );
        query.setParameter("name",name);
        query.setParameter("brand",brand);
        return (Long)query.getSingleResult() > 0L;
    }
}
