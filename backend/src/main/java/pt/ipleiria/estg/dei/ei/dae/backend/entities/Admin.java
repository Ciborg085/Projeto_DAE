package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User {

    // Required
    public Admin() {
    }
    public Admin(String username, String password, String name, String email) {
        super(username, password, name, email);
    }


}
