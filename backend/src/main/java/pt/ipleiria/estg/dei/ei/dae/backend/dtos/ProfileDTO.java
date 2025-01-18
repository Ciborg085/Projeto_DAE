package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import jakarta.persistence.DiscriminatorColumn;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.User;

public class ProfileDTO {
    private String username;
    private String email;

    private String role;

    public ProfileDTO() {
    }

    public ProfileDTO(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static ProfileDTO from(User user) {
        return new ProfileDTO(
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }
}
