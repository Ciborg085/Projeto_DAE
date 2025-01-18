package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.User;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    // 🔎 Buscar utilizador pelo username
    public User find(String username) throws MyEntityNotFoundException {
        User user = entityManager.find(User.class, username);
        if (user == null) {
            throw new MyEntityNotFoundException("Utilizador '" + username + "' não encontrado.");
        }
        return user;
    }

    public User findOrFail(String username) {
        User user = entityManager.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        try {
            User user = this.find(username);
            return user != null && user.getPassword().equals(hasher.hash(password));
        } catch (MyEntityNotFoundException e) {
            return false;
        }
    }

    // 🔐 Autenticação de utilizador (login)
    public User authenticate(String username, String password) throws MyEntityNotFoundException {
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username AND u.password = :password",
                    User.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);  // TODO Implementar hashing depois!

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            throw new MyEntityNotFoundException("Credenciais inválidas.");
        }
    }

    // 🔍 Verificar se um utilizador já existe
    public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(u.username) FROM User u WHERE u.username = :username"
        );
        query.setParameter("username", username);
        return (Long) query.getSingleResult() > 0;
    }

    // 📝 Registar um novo utilizador (cliente)
    public void registerClient(String username, String password, String name, String email) throws MyEntityExistsException {
        if (exists(username)) {
            throw new MyEntityExistsException("Utilizador com o username '" + username + "' já existe.");
        }

        Client client = new Client(username, hasher.hash(password), name, email);
        entityManager.persist(client);
    }
}
