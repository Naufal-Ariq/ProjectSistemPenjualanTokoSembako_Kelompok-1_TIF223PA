/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import com.mycompany.projectpenjualantokosembako.DatabaseConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginOperations {

    // Validasi kredensial pengguna
    public String validateCredentials(String username, String password) {
        String role = null;
        String query = "SELECT password, role FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String hashedPassword = hashPassword(password); // Hash password yang dimasukkan
                if (hashedPassword.equals(storedPassword)) {
                    role = rs.getString("role"); // Ambil peran pengguna jika password cocok
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role; // Kembalikan role jika valid, atau null jika tidak valid
    }

public boolean registerUser(String username, String password, String email) throws SQLException {
    String hashedPassword = hashPassword(password); // Hash the password for security
    String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        stmt.setString(3, email);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; // Return true if the user was added successfully
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Return false if there was an error
    }
}

 public boolean validateUser (String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // Consider hashing the password
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a matching user is found
        }
    }

    // Fungsi untuk hash password menggunakan MD5
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b)); // Ubah byte ke format heksadesimal
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
