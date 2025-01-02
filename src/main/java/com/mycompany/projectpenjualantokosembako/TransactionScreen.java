/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectpenjualantokosembako;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransactionScreen {
    public static void display() {
        Stage stage = new Stage();
        stage.setTitle("Transaksi");

        // UI Components
        Label productIdLabel = new Label("ID Produk:");
        TextField productIdField = new TextField();
        Label quantityLabel = new Label("Jumlah:");
        TextField quantityField = new TextField();
        Button calculateButton = new Button("Hitung Total");
        Label totalLabel = new Label("Total: Rp 0");
        Button saveButton = new Button("Simpan Transaksi");

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(productIdLabel, productIdField, quantityLabel, quantityField, calculateButton, totalLabel, saveButton);

        // Calculate action
        calculateButton.setOnAction(e -> {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                double pricePerUnit = getPriceFromDatabase(productId);
                double total = pricePerUnit * quantity;
                totalLabel.setText("Total: Rp " + total);
            } catch (NumberFormatException ex) {
                totalLabel.setText("Input tidak valid.");
            }
        });

        // Save action
        saveButton.setOnAction(e -> {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                double total = Double.parseDouble(totalLabel.getText().replace("Total: Rp ", ""));

                Transaction transaction = new Transaction();
                transaction.setProductId(productId);
                transaction.setQuantity(quantity);
                transaction.setTotal(total);
                transaction.save();

                stage.close();
            } catch (NumberFormatException ex) {
                totalLabel.setText("Input tidak valid atau total belum dihitung.");
            }
        });

        // Scene and stage
        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private static double getPriceFromDatabase(int productId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT price FROM products WHERE id = ?";
            var stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            var result = stmt.executeQuery();

            if (result.next()) {
                return result.getDouble("price");
            } else {
                return 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}

