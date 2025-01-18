package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

@Stateless
public class AdministratorBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String email) {
        Administrator administrator = new Administrator(username, hasher.hash(password), name, email);
        entityManager.persist(administrator);
    }
}
