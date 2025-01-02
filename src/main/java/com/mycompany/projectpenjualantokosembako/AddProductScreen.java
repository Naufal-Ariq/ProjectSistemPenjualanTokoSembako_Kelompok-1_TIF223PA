/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectpenjualantokosembako;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddProductScreen {
    public static void display() {
        Stage stage = new Stage();
        stage.setTitle("Tambah Produk");

        // UI Components
        Label nameLabel = new Label("Nama Produk:");
        TextField nameField = new TextField();
        Label priceLabel = new Label("Harga:");
        TextField priceField = new TextField();
        Label stockLabel = new Label("Stok:");
        TextField stockField = new TextField();
        Button saveButton = new Button("Simpan");

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(nameLabel, nameField, priceLabel, priceField, stockLabel, stockField, saveButton);

        // Save action
        saveButton.setOnAction(e -> {
            try {
                Product product = new Product();
                product.setName(nameField.getText());
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setStock(Integer.parseInt(stockField.getText()));
                product.save();
                stage.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Input tidak valid.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Scene and stage
        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }
}

