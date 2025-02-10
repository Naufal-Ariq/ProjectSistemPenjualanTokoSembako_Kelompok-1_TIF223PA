package Admin;

import Dashboard.User;
import java.sql.Timestamp;

public class AdminAccount extends Dashboard.User {
    private String username;
    private String password;
    private Timestamp createdAt;
    private String role;

    public AdminAccount(int id, String username, String email, String password, Timestamp createdAt, String role) {
        super(id, username, email);
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
         // Getter untuk email
    public String getEmail() {
        return super.getEmail(); // Mengambil email dari superclass User
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Admin ID: " + getId());
        System.out.println("Username: " + getUsername());
        System.out.println("Email: " + getEmail());
        System.out.println("Role: " + getRole());
    }
}
