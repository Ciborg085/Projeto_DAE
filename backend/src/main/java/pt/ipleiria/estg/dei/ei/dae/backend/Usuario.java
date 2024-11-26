package pt.ipleiria.estg.dei.ei.dae.academics.entities;

public class Usuario {
    private  int id;
    public  String username;
    public  String password;
    public  String email;

    public Usuario() {}

    public Usuario(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
