package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "Administrator")
public class Administrator extends User {

    // Required
    public Administrator() {
    }
    public Administrator(String username, String password, String name, String email) {
        super(username, password, name, email);
    }


}
