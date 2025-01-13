package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ClientBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email)
            throws MyConstraintViolationException, MyEntityExistsException, MyEntityNotFoundException {
        if (exists(username)) {
            throw new MyEntityExistsException("Client with username '" + username +"' already exists");
        }

        if (username == null) {
            throw new MyEntityNotFoundException("Username is empty");
        }

        try {
            Client client = new Client(username, password, name, email);
            entityManager.persist(client);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

   public List<Client> findAll() {
        return entityManager.createNamedQuery("getAllClients", Client.class).getResultList();
   }

   public Client find(String username) throws MyEntityNotFoundException {
       var client = entityManager.find(Client.class, username);
       if (client == null) {
           throw new MyEntityNotFoundException("client " + username + " not found");
       }
       return client;
   }

   public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(c.username) FROM Client c WHERE c.username = :username",
                Long.class
        );
        query.setParameter("username",username);
        return (Long)query.getSingleResult() > 0L;
   }

   public void update(String username, String password, String name, String email)
           throws MyEntityNotFoundException {
        var client = entityManager.find(Client.class,username);
        if (client == null) {
           throw new MyEntityNotFoundException("UPDATE: Client " + username + "not found");
        }

        entityManager.lock(client, LockModeType.OPTIMISTIC);
        client.setName(name);
        client.setPassword(password);
        client.setEmail(email);
   }

}
