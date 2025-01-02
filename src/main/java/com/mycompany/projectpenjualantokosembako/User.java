/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectpenjualantokosembako;

import java.sql.Connection;
import java.sql.SQLException;

public class User extends BaseEntity {
    private String username;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void save() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, role) VALUES (?, ?)";
            var stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, role);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
