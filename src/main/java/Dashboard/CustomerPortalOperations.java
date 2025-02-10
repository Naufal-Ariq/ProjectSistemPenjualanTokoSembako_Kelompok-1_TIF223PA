package Dashboard;

import com.mycompany.projectpenjualantokosembako.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class CustomerPortalOperations {

    // Add a new customer to the database
    public void addCustomer(String name, String email, String phone, String address) {
        String query = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            stmt.executeUpdate();
            System.out.println("Customer added to the database: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all customers from the database
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Add an order to the Orders database
    public void addOrder(String customerEmail, double totalPrice) {
        String query = "INSERT INTO Orders (customer_email, total_price, order_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customerEmail);
            stmt.setDouble(2, totalPrice);
            stmt.setObject(3, LocalDateTime.now()); // Set the current date and time
            stmt.executeUpdate();
            System.out.println("Order added for customer: " + customerEmail);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Clear the cart (remove items from the cart in the database)
    public void clearCart(String customerEmail) {
        String query = "DELETE FROM cart WHERE customer_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customerEmail);
            stmt.executeUpdate();
            System.out.println("Cart cleared for customer: " + customerEmail);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add items to the order_items database
    public void addItemsToOrderItems(int orderId, List<CartItem> cartItems) {
        String query = "INSERT INTO order_items (order_id, name, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (CartItem item : cartItems) {
                stmt.setInt(1, orderId); // Use the order ID from the order created
                stmt.setString(2, item.getProductName()); // Assuming you want to store the product name
                stmt.setInt(3, item.getQuantity());
                stmt.setDouble(4, item.getPrice());
                stmt.executeUpdate();
            }
            System.out.println("Items added to order_items for order ID: " + orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the last order ID
    public int getLastOrderId() {
        String query = "SELECT MAX(order_id) AS last_id FROM Orders";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("last_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no order ID is found
    }

    // Get cart items for a specific customer
    public List<CartItem> getCartItems(String customerEmail) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart WHERE customer_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customerEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cartItems.add(new CartItem(
                        rs.getString("brand_name"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
    
    public void addToCart(String customerEmail, String brand, String product_name, int quantity, double price) {
    String query = "INSERT INTO cart (customer_email, brand_name, product_name, quantity, price) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, customerEmail);
        stmt.setString(2, brand);
        stmt.setString(3, product_name);
        stmt.setInt(4, quantity);
        stmt.setDouble(5, price);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Calculate the total price of all items in the cart for a customer
    public double calculateTotalPrice(String customerEmail) {
        double total = 0.0;
        List<CartItem> cartItems = getCartItems(customerEmail);
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // Get the product price from the database
    public double getProductPrice(String productName) {
        String query = "SELECT price FROM products WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Update customer details
    public void updateCustomer(String email, String newName, String newPhone, String newAddress) {
        String sql = "UPDATE customers SET name = ?, phone = ?, address = ? WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setString(2, newPhone);
            stmt.setString(3, newAddress);
            stmt.setString(4, email);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer updated: " + newName);
            } else {
                System.out.println("No customer found with email: " + email);
            }
        } catch (SQLException e) {
            System.err.println("Error updating customer in database.");
            e.printStackTrace();
        }
    }

    // Process an order and add items to the order_items table
    public void processOrder(String customerEmail, List<CartItem> cartItems) {
        String orderQuery = "INSERT INTO Orders (customer_email, total_price) VALUES (?, ?)";
        String itemQuery = "INSERT INTO order_items (order_id, name, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                double totalPrice = calculateTotalPrice(customerEmail);
                orderStmt.setString(1, customerEmail);
                orderStmt.setDouble(2, totalPrice);
                orderStmt.executeUpdate();

                ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    try (PreparedStatement itemStmt = conn.prepareStatement(itemQuery)) {
                        for (CartItem item : cartItems) {
                            itemStmt.setInt(1, orderId);
                            itemStmt.setString(2, item.getProductName());
                            itemStmt.setInt(3, item.getQuantity());
                            itemStmt.setDouble(4, item.getPrice());
                            itemStmt.executeUpdate();
                        }
                    }
                }
                conn.commit(); // Commit transaction
                System.out.println("Order processed for customer: " + customerEmail);
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                System.err.println("Error processing order for customer: " + customerEmail);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Process payment for an order
    public boolean processPayment(double totalPrice) {
        // Simulate payment processing logic
        // In a real application, this would involve integration with a payment gateway
        return totalPrice > 0; // Assume payment is successful if total price is greater than zero
    }


    // Add payment record to the database
public void addPayment(int orderId, double amount, String paymentStatus, LocalDateTime paymentDate) {
    String query = "INSERT INTO payments (order_id, amount, payment_status, payment_date) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, orderId);
        stmt.setDouble(2, amount);
        stmt.setString(3, paymentStatus);
        stmt.setTimestamp(4, Timestamp.valueOf(paymentDate)); // Mengonversi LocalDateTime ke Timestamp
        stmt.executeUpdate();
        System.out.println("Payment record added successfully for order ID: " + orderId);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    }