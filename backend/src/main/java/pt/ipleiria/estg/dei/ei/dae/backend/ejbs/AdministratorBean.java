package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Administrator;

@Stateless
public class AdministratorBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email) {
        Administrator administrator = new Administrator(username, password, name, email);
        entityManager.persist(administrator);
    }
}
