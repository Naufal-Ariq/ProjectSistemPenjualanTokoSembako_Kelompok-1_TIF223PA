/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectpenjualantokosembako;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction extends BaseEntity {
    private int productId;
    private int quantity;
    private double total;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public void save() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO transactions (product_id, quantity, total) VALUES (?, ?, ?)";
            var stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, total);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


