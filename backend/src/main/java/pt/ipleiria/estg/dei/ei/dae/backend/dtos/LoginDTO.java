package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class LoginDTO implements Serializable {
    @NotBlank(message = "Username can't be blank")
    private String username;
    @NotBlank(message = "Password can't be blank")
    private String password;

    public LoginDTO() {}

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
