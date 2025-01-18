package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.ApplicationException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    public void create(long id, String name, String brand, float price,int quantityOrdered, String category, int maxQuantityPerVolume, String typeOfPackage, long order_id)
            throws MyEntityExistsException, MyEntityNotFoundException {
        if(exists(id)) {
            throw new MyEntityExistsException( "Product with id "+id+" already exists" );
        }

        Order order = orderBean.find(order_id);

        var product = new Product(id, name, brand, price, quantityOrdered, category , maxQuantityPerVolume, typeOfPackage, order);
        entityManager.persist(product);
        order.addProduct(product);
        entityManager.flush();
    }

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.id) FROM Product p WHERE p.id = :id",
                Long.class
        );
        query.setParameter("id",id);
        return (Long)query.getSingleResult() > 0L;
    }

    public Product find(long id) throws MyEntityNotFoundException{
        var product = entityManager.find(Product.class, id);
        if(product == null) {
            throw new MyEntityNotFoundException("ProductBean::find: product " + id + " not found");
        }
        return product;
    }

    public List<Product> findAll() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public List<Product> findAll(String username) {
        return entityManager.createNamedQuery("getAllProductsWhereUsername", Product.class)
                .setParameter("client_username",username)
                .getResultList();
    }

    public void update(
            long id,
            String name,
            String brand,
            float price,
            int maxQuantityPerVolume,
            String typeOfPackage) throws MyEntityNotFoundException, MyEntityExistsException {

        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with id " + id + " not found");
        }

        if (exists(id)) {
            throw new MyEntityExistsException(
                    "Product with id "+id+" already exists");
        }

        entityManager.lock(product, LockModeType.OPTIMISTIC);
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(price);
        product.setMaxQuantityPerVolume(maxQuantityPerVolume);
        product.setTypeOfPackage(typeOfPackage);
    }
}
