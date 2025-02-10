package Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProductView {

    private ProductOperations productOps = new ProductOperations();

    public void showProductView(Stage primaryStage) {
        primaryStage.setTitle("Product View");

        // UI Elements
        Label nameLabel = new Label("Product Name:");
        ComboBox<String> nameComboBox = new ComboBox<>();
        ObservableList<String> productNames = FXCollections.observableArrayList();
        nameComboBox.setItems(productNames);

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        priceField.setEditable(false);

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Button addButton = new Button("Add Product");
        Button updateButton = new Button("Update Product");
        Button deleteButton = new Button("Delete Product");

        Label messageLabel = new Label();

        // Populate ComboBox with product names
        ObservableList<Product> products = productOps.getAllProducts();
        for (Product p : products) {
            productNames.add(p.getName());
        }

        // Set actions for ComboBox
        nameComboBox.setOnAction(e -> {
            String selectedProductName = nameComboBox.getValue();
            Product selectedProduct = productOps.findProductByName(selectedProductName);

            if (selectedProduct != null) {
                priceField.setText(String.valueOf(selectedProduct.getPrice()));
                quantityField.setText(String.valueOf(selectedProduct.getStock()));
            }
        });

        // Add product
        addButton.setOnAction(e -> {
            String productName = nameComboBox.getValue();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            Product newProduct = new Product(productName, price, quantity);
            productOps.addProduct(newProduct);

            // Update product names in ComboBox after adding
            productNames.add(productName);

            messageLabel.setText("Product added: " + productName);
        });

        updateButton.setOnAction(e -> {
        String productName = nameComboBox.getValue();
        Product selectedProduct = productOps.findProductByName(productName);

        if (selectedProduct != null) {
        try {
            double newPrice = Double.parseDouble(priceField.getText());
            int newStock = Integer.parseInt(quantityField.getText());

            // Call the update method on the ProductOperations instance
            productOps.updateProduct(selectedProduct.getId(), productName, newPrice, newStock);

            messageLabel.setText("Product updated: " + productName);
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid input. Please enter valid numbers for price and quantity.");
        }
      } else {
        messageLabel.setText("Product not found.");
      }
        });
        
        // Delete product
        deleteButton.setOnAction(e -> {
            String productName = nameComboBox.getValue();
            Product selectedProduct = productOps.findProductByName(productName);

            if (selectedProduct != null) {
                productOps.deleteProduct(selectedProduct);

                // Remove the product from ComboBox
                productNames.remove(productName);

                messageLabel.setText("Product deleted: " + productName);
            }
        });

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameComboBox, 1, 0);
        gridPane.add(priceLabel, 0, 1);
        gridPane.add(priceField, 1, 1);
        gridPane.add(quantityLabel, 0, 2);
        gridPane.add(quantityField, 1, 2);
        gridPane.add(addButton, 0, 3);
        gridPane.add(updateButton, 1, 3);
        gridPane.add(deleteButton, 0, 4);
        gridPane.add(messageLabel, 1, 5);

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
