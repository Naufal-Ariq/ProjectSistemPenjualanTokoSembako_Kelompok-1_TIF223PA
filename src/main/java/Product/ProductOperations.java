package Product;

import com.mycompany.projectpenjualantokosembako.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ProductOperations {

    // CREATE: Add a new product
    public static void addProduct(Product product) {
        String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product added: " + product.getName());
            }
        } catch (SQLException e) {
            System.err.println("Error adding product to database.");
            e.printStackTrace();
        }
    }

    // READ: Retrieve all products
    public static ObservableList<Product> getAllProducts() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching products from database.");
            e.printStackTrace();
        }

        return productList;
    }

    // UPDATE: Update product details
    public static void updateProduct(int id, String newName, double newPrice, int newStock) {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setDouble(2, newPrice);
            stmt.setInt(3, newStock);
            stmt.setInt(4, id); // Use the ID passed as a parameter

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product updated: " + newName);
            } else {
                System.out.println("No product found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error updating product in database.");
            e.printStackTrace();
        }
    }

    // DELETE: Remove a product
    public static void deleteProduct(Product product) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product deleted: " + product.getName());
            } else {
                System.out.println("No product found with ID: " + product.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error deleting product from database.");
            e.printStackTrace();
        }
    }

    // FIND: Search for a product by name
    public static Product findProductByName(String productName) {
        String sql = "SELECT * FROM products WHERE name = ?";
        Product product = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            } else {
                System.out.println("No product found with name: " + productName);
            }
        } catch (SQLException e) {
            System.err.println("Error finding product by name.");
            e.printStackTrace();
        }

        return product;
    }

    // GET: Retrieve the price of a specific product
    public static double getProductPrice(String productName) {
        String sql = "SELECT price FROM products WHERE name = ?";
        double price = 0.0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                price = rs.getDouble("price");
            } else {
                System.out.println("No product found with name: " + productName);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving product price.");
            e.printStackTrace();
        }

        return price;
    }
}