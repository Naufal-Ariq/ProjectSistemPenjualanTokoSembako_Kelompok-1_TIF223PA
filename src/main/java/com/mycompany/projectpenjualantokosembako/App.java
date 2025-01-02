package com.mycompany.projectpenjualantokosembako;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

     @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Buttons for navigation
        Button btnAddProduct = new Button("Tambah Produk");
        Button btnTransaction = new Button("Transaksi");

        btnAddProduct.setOnAction(e -> AddProductScreen.display());
        btnTransaction.setOnAction(e -> TransactionScreen.display());

        layout.getChildren().addAll(btnAddProduct, btnTransaction);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Toko Sembako");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

