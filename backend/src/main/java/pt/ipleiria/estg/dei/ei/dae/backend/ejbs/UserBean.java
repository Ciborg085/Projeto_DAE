package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.User;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager entityManager;

    private User find(String username)
            throws MyEntityNotFoundException {
        User user = entityManager.find(User.class,username);
        if (user == null) {
            throw new MyEntityNotFoundException("user " + username + " not found");
        }
        return user;
    }
}
