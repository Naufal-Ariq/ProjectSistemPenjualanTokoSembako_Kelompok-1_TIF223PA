package Dashboard;

import Login.LoginView;
import Product.ProductOperations; // Import the ProductOperations class
import java.time.LocalDateTime;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class CustomerPortal {
    private final CustomerPortalOperations operations = new CustomerPortalOperations();
    private TableView<CartItem> cartTable;
    private Label totalLabel;
    private ComboBox<String> productComboBox;
    private ImageView productImageView;

    // New fields for customer details
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField addressField;
    private TextField brandField; // New field for brand name
    private Label priceLabel; // New label for displaying product price
    private Spinner<Integer> quantitySpinner; // Declare quantitySpinner as a class-level variable
    private Stage primaryStage; // Reference to the primary stage
    private String userEmail; // Email pengguna yang terdaftar
    private ProductOperations productOperations; // Untuk mengelola produk

    public CustomerPortal(Stage primaryStage, String Email) {
        this.primaryStage = primaryStage;
        this.userEmail = Email;
        this.productOperations = new ProductOperations(); // Inisialisasi ProductOperations
        showDashboard(); // Memanggil metode untuk menginisialisasi antarmuka pengguna
    }

    public void showDashboard() {
        // Sidebar
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #d3d3d3;");

        Label welcomeLabel = new Label("Welcome, Toko Sembako");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

         // Load the image for the Purchase button
    Image purchaseImage = new Image(getClass().getResourceAsStream("/Images/Icon_purchase.jpeg")); // Adjust the path as necessary
    ImageView purchaseImageView = new ImageView(purchaseImage);
    purchaseImageView.setFitWidth(40); // Set the width of the image
    purchaseImageView.setFitHeight(40); // Set the height of the image

    Button purchaseButton = new Button("Purchase", purchaseImageView); // Set the image on the button
    purchaseButton.setPrefWidth(200);

    Button logoutButton = new Button("Logout");
    logoutButton.setPrefWidth(200);
    logoutButton.setOnAction(e -> logout()); // Set action for logout button

    sidebar.getChildren().addAll(welcomeLabel, purchaseButton, logoutButton);

        // Cart Table
        cartTable = new TableView<>();
        String customerEmail = "customer@example.com"; // Replace with actual customer email
        cartTable.setItems(FXCollections.observableArrayList(operations.getCartItems(customerEmail)));
        cartTable.setPlaceholder(new Label("No content in table"));
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<CartItem, String> brandColumn = new TableColumn<>("Brand Name");
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrandName()));

        TableColumn<CartItem, String> productColumn = new TableColumn<>("Product Name");
        productColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));

        TableColumn<CartItem, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        TableColumn<CartItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        cartTable.getColumns().addAll(brandColumn, productColumn, quantityColumn, priceColumn);
        
        // Input Fields for Adding Customer
        VBox inputSection = new VBox(10);
        inputSection.setPadding(new Insets(10));

        // Customer Details
        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter customer name");

        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("Enter customer email");

        Label phoneLabel = new Label("Phone:");
        phoneField = new TextField();
        phoneField.setPromptText("Enter customer phone");

        Label addressLabel = new Label("Address:");
        addressField = new TextField();
        addressField.setPromptText("Enter customer address");

                Button addCustomerButton = new Button("Add Customer");
        addCustomerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        

        // Add customer button action
        addCustomerButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields!");
                alert.show();
                return;
            }

            operations.addCustomer(name, email, phone, address);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer added successfully!");
            alert.show();
            clearCustomerFields(); // Clear fields after adding
        });

        // Add customer input fields to the input section
        inputSection.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, phoneLabel, phoneField, addressLabel, addressField, addCustomerButton);

        // Product Selection and Cart Management
        
        Label brandLabel = new Label("Brand Name:");
        brandField = new TextField();
        brandField.setPromptText("Enter brand name");
        
        Label productLabel = new Label("Product Name:");
        ObservableList<String> products = FXCollections.observableArrayList("Beras", "Minyak Goreng", "Telur", "Terigu");
        double[] prices = {10000.0, 14000.0, 2000.0, 7000.0}; // Prices for the products
        String[] imagePaths = {"/images/beras.jpg", "/images/minyak_goreng.jpg", "/images/telur.jpg", "/images/terigu.jpg"};

        productComboBox = new ComboBox<>(products);
        productComboBox.setPromptText("Pilih Produk");

        // ImageView to display selected product
        productImageView = new ImageView();
        productImageView.setFitWidth(100);
        productImageView.setPreserveRatio(true);

        // Initialize priceLabel
        priceLabel = new Label("Price: $0.0");
        
        productComboBox.setCellFactory(lv -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    int index = products.indexOf(item);
                    if (index >= 0) {
                        Image image = new Image(getClass().getResourceAsStream(imagePaths[index]));
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setPreserveRatio(true);
                        setText(item);
                        setGraphic(imageView);
                    }
                }
            }
        });

        productComboBox.setOnAction(event -> {
            int index = products.indexOf(productComboBox.getValue());
            if (index >= 0) {
                Image image = new Image(getClass().getResourceAsStream(imagePaths[index]));
                productImageView.setImage(image);
                
                // Update the price label based on the selected product
                double price = prices[index]; // Get the price from the prices array
                priceLabel.setText("Price: $" + price); // Update the price label
                
                // Update the total price based on the selected quantity
                int quantity = quantitySpinner.getValue();
                double totalPrice = price * quantity;
                totalLabel.setText("Total: $" + totalPrice); // Update the total label
            }
        });

        Label quantityLabel = new Label("Quantity:");
        quantitySpinner = new Spinner<>(1, 100, 1); // Initialize quantitySpinner here

        // Update total when quantity changes
        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            int index = products.indexOf(productComboBox.getValue());
            if (index >= 0) {
                double price = prices[index];
                double totalPrice = price * newValue; // Calculate total based on new quantity
                totalLabel.setText("Total: $" + totalPrice); // Update the total label
            }
        });

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        HBox productSelection = new HBox(10, brandLabel, brandField,productComboBox, productImageView);
        inputSection.getChildren().addAll(productLabel, productSelection, quantityLabel, quantitySpinner, addButton, priceLabel); // Add priceLabel to the input section

        // Total and Action Buttons
        totalLabel = new Label("Total: $0.0");
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button receiptButton = new Button("Receipt");
        Button payButton = new Button("Pay");
        Button resetButton = new Button("Reset");

        HBox actionButtons = new HBox(10, receiptButton, payButton, resetButton);
        actionButtons.setSpacing(15);
        VBox rightSection = new VBox(10, inputSection, totalLabel, actionButtons);
        rightSection.setPadding(new Insets(10));

        HBox mainLayout = new HBox(sidebar, cartTable, rightSection);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 900, 400);
        primaryStage.setTitle("Toko Sembako System | Customer Portal");
        primaryStage.setScene(scene);
        primaryStage.show();

addButton.setOnAction(e -> {
    String brand = brandField.getText().trim();
    String product = productComboBox.getValue();
    int quantity = quantitySpinner.getValue();

    // Validasi input
    if (brand.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Brand name cannot be empty!");
        alert.show();
        return;
    }
    if (product == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a product!");
        alert.show();
        return;
    }

    // Ambil harga produk dari daftar harga
    double price = prices[products.indexOf(product)];
    double totalPrice = price * quantity;

    // Tambahkan order ke database
    operations.addOrder(customerEmail, totalPrice);

    // Ambil ID order terbaru
    int orderId = operations.getLastOrderId();

    // Tambahkan item ke order_items di database
    operations.addItemsToOrderItems(orderId, List.of(new CartItem(brand, product, quantity, price)));

    // **Tambahkan ke cart (penting!)**
    operations.addToCart(customerEmail, brand, product, quantity, price);

    // 🚀 Perbarui tabel dengan mengambil ulang data dari database
    ObservableList<CartItem> updatedCartItems = FXCollections.observableArrayList(operations.getCartItems(customerEmail));
    
    // Pastikan data di-refresh
    cartTable.setItems(updatedCartItems);
    cartTable.refresh();

    // Perbarui total harga
    updateTotalPrice(customerEmail);
});

payButton.setOnAction(e -> {
    List<CartItem> cartItems = operations.getCartItems(customerEmail);
    System.out.println("Customer Email: " + customerEmail);
    System.out.println("Cart Items Count: " + cartItems.size());

    if (cartItems.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "No items in the cart to process the order!");
        alert.show();
        return;
    }

    double totalPrice = operations.calculateTotalPrice(customerEmail); // Hitung total harga
    boolean paymentSuccess = operations.processPayment(totalPrice); // Proses pembayaran

    if (paymentSuccess) {
        // Dapatkan ID pesanan terakhir
        int orderId = operations.getLastOrderId(); // Ambil ID pesanan terakhir

        // Catat pembayaran
        operations.addPayment(orderId, totalPrice, "Success", LocalDateTime.now()); // Catat pembayaran sebagai berhasil

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Payment successful! Your order has been processed.");
        alert.show();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Payment failed! Please try again.");
        alert.show();
    }

    cartTable.setItems(FXCollections.observableArrayList(operations.getCartItems(customerEmail)));
    updateTotalPrice(customerEmail);
});

    }
   private void logout() {
    // Create a confirmation dialog
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout Confirmation");
    alert.setHeaderText("Are you sure you want to log out?");
    alert.setContentText("Click OK to log out, or Cancel to stay logged in.");

    // Show the dialog and wait for a response
    Optional<ButtonType> result = alert.showAndWait();

    // Check the user's response
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // User chose OK, proceed with logout
        LoginView loginView = new LoginView(primaryStage);
        loginView.showLoginView(); // Show the login view
    } else {
        // User chose Cancel, do nothing (stay logged in)
        System.out.println("Logout canceled.");
    }
}



    private void updateTotalPrice(String customerEmail) {
        double total = operations.calculateTotalPrice(customerEmail);
        totalLabel.setText("Total: $" + total);
    }

    private void clearCustomerFields() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        brandField.clear(); // Clear brand field as well
    }
}