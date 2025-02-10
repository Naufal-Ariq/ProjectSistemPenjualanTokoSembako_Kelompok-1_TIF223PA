package Dashboard;

import Admin.AdminAccount;
import Admin.AdminAccountOperations;
import Login.LoginView;
import Product.Product;
import Product.ProductOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AdminDashboard {

    private TableView<Product> productTable;
    private TextField stockField;
    private TextField priceField;
    private ComboBox<String> productComboBox;

    public AdminDashboard(Stage stage) {
        stage.setTitle("Admin Dashboard - Toko Sembako");

        // Sidebar Navigation
        VBox sidebar = new VBox();
        sidebar.setPadding(new Insets(20));
        sidebar.setSpacing(15);
        sidebar.setStyle("-fx-background-color: #d3d3d3;");

        // Main Content Area
        VBox contentArea = new VBox();
        contentArea.setSpacing(20);
        contentArea.setPadding(new Insets(20));

        Button addProductionButton = createFeatureButton("/Images/Icon Tambah_produk.jpg", "Tambah Produk", contentArea);
        Button accountsButton = createFeatureButton("/Images/Icon_akun.jpg", "Akun", contentArea);
        Button dashboardButton = createFeatureButton("/Images/Icon_dashboard.jpeg", "Dashboard", contentArea);
        
        // Add buttons to the sidebar
        sidebar.getChildren().addAll(dashboardButton, addProductionButton, accountsButton);

        // Logout Button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> logout(stage)); // Set action for logout button
        sidebar.getChildren().add(logoutButton);

        // Top Bar
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);
        topBar.setStyle("-fx-background-color: #ADD8E6;");

        // Hamburger Menu Button
        Button hamburgerButton = new Button();
        Image burgerImage = new Image(getClass().getResourceAsStream("/Images/Icon_hamburger.jpg"));
        ImageView burgerImageView = new ImageView(burgerImage);
        burgerImageView.setFitWidth(40);
        burgerImageView.setFitHeight(40);
        hamburgerButton.setGraphic(burgerImageView);
        hamburgerButton.setPrefSize(50, 50);
        hamburgerButton.setOnMouseClicked((MouseEvent event) -> toggleSidebar(sidebar, stage));

        // Top Bar Content
        Label dashboardLabel = new Label("Dashboard");
        topBar.getChildren().addAll(hamburgerButton, dashboardLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Main Layout
        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setTop(topBar);
        root.setCenter(contentArea);

        // Scene Setup
        Scene scene = new Scene(root, 800, 600);

        // Load CSS
        URL cssURL = getClass().getResource("/Dashboard/dashboard.css");
        if (cssURL != null) {
            scene.getStylesheets().add(cssURL.toExternalForm());
        } else {
            System.err.println("Error: CSS file not found! Check the resource path.");
        }

        stage.setScene(scene);
        stage.show();
    
        // Load the dashboard by default
        loadDashboard(contentArea);
    }

    private Button createFeatureButton(String imagePath, String text, VBox contentArea) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30); // Icon size
        imageView.setFitHeight(30);

        Button button = new Button(text, imageView);
        button.setContentDisplay(ContentDisplay.LEFT);
        button.setPrefWidth(180);
        button.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d3d3d3; -fx-padding: 10;");

        button.setOnAction(e -> {
            if ("Tambah Produk".equals(text)) {
                loadAddProductForm(contentArea);
            } else if ("Akun".equals(text)) {
                loadAdminAccountManagement(contentArea);
            } else if ("Dashboard".equals(text)) {
                loadDashboard(contentArea);
            }
            
            System.out.println(text + " clicked!");
        });

        return button;
    }

    private void loadDashboard(VBox contentArea) {
        contentArea.getChildren().clear(); // Clear current content

        // Dashboard Title
        Label titleLabel = new Label("Dashboard");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Cards Section
        HBox cardsSection = new HBox(20);
        cardsSection.setAlignment(Pos.CENTER);

        VBox card1 = createDashboardCard("Active Customers", "2", "#1E3A8A", "/Images/Icon_Customers.jpg");
        VBox card2 = createDashboardCard("Today's Income", "RP14.0", "#6B21A8", "/Images/Icon_todays_income.jpeg");
        VBox card3 = createDashboardCard("Total Income", "RP2888.0", "#9B2C2C", "/Images/Icon_total_income.jpeg");

        cardsSection.getChildren().addAll(card1, card2, card3);

        // Chart Section
        VBox chartSection = new VBox(20);
        chartSection.setAlignment(Pos.CENTER);
        chartSection.setPadding(new Insets(20));
        chartSection.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border-radius: 5;");

        Label chartTitle = new Label("Income Chart Data");
        chartTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Image chartImage = new Image(getClass().getResourceAsStream("/Images/Icon_chart.jpeg"));
        ImageView chartImageView = new ImageView(chartImage);
        chartImageView.setFitWidth(400);
        chartImageView.setFitHeight(200);

        chartSection.getChildren().addAll(chartTitle, chartImageView);

        // Add sections to content area
        contentArea.getChildren().addAll(titleLabel, cardsSection, chartSection);
    }

    private VBox createDashboardCard(String title, String value, String color, String iconPath) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 5;");
        card.setAlignment(Pos.CENTER);

        Image icon = new Image(getClass().getResourceAsStream(iconPath));
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(50);
        iconView.setFitHeight(50);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white;");

        card.getChildren().addAll(iconView, valueLabel, titleLabel);

        return card;
    }
    
    private void loadAddProductForm(VBox contentArea) {
        contentArea.getChildren().clear(); // Clear current content

        // Form Section (Left)
        Label titleLabel = new Label("Tambah Produk");
        titleLabel.setStyle("-fx-font-size:  18px; -fx-font-weight: bold;");

        stockField = new TextField();
        stockField.setPromptText("Stok Produk");

        // Dropdown ComboBox with product options and images
        ObservableList<String> products = FXCollections.observableArrayList("Beras", "Minyak Goreng", "Telur", "Terigu");
        double[] prices = {10000.0, 14000.0, 2000.0, 7000.0};
        String[] imagePaths = {"/images/beras.jpg", "/images/minyak_goreng.jpg", "/images/telur.jpg", "/images/terigu.jpg"};

        productComboBox = new ComboBox<>(products);
        productComboBox.setPromptText("Pilih Produk");

        // Display images in dropdown
        productComboBox.setCellFactory(lv -> new ListCell<String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(item);
                    int index = products.indexOf(item);
                    if (index >= 0) {
                        String imagePath = imagePaths[index];
                        imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
                        imageView.setFitWidth(30); // Set image size
                        imageView.setPreserveRatio(true);
                    }
                    setGraphic(imageView);
                }
            }
        });

        priceField = new TextField();
        priceField.setEditable(false);
        priceField.setPromptText("Harga Produk");

        // Set price when a product is selected
        productComboBox.setOnAction(e -> {
            int selectedIndex = productComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                priceField.setText(String.valueOf(prices[selectedIndex]));
            }
        });

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button clearButton = new Button("Clear");
        Button deleteButton = new Button("Delete");

        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        updateButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        clearButton.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        addButton.setOnAction(e -> {
            try {
                // Create Product object from form data
                Product product = new Product(productComboBox.getSelectionModel().getSelectedItem(),
                        Double.parseDouble(priceField.getText()),
                        Integer.parseInt(stockField.getText()));
                ProductOperations.addProduct(product); // Add product to database
                productTable.setItems(ProductOperations.getAllProducts()); // Refresh table
                clearFields();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter valid numbers for price and stock.");
            }
        });

        updateButton.setOnAction(e -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                try {
                    String newName = productComboBox.getSelectionModel().getSelectedItem();
                    if (newName == null || newName.isEmpty()) {
                        System.out.println("Please select a valid product name.");
                        return;
                    }

                    double newPrice = Double.parseDouble(priceField.getText());
                    int newStock = Integer.parseInt(stockField.getText());

                    // Call the update method on the ProductOperations instance
                    ProductOperations.updateProduct(selectedProduct.getId(), newName, newPrice, newStock);

                    productTable.setItems(ProductOperations.getAllProducts()); // Refresh table
                    clearFields(); // Clear input fields after update
                    System.out.println("Product updated successfully.");
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter valid numbers for price and stock.");
                }
            } else {
                System.out.println("Please select a product to update.");
            }
        });

        clearButton.setOnAction(e -> clearFields());

        deleteButton.setOnAction(e -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                ProductOperations.deleteProduct(selectedProduct);
                productTable.setItems(ProductOperations.getAllProducts()); // Refresh table
                clearFields();
            } else {
                System.out.println("Please select a product to delete.");
            }
        });

        VBox formSection = new VBox(10, titleLabel, productComboBox, priceField, stockField,
                new HBox(10, addButton, updateButton),
                new HBox(10, clearButton, deleteButton));
        formSection.setPadding(new Insets(20));
        formSection.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        formSection.setAlignment(Pos.TOP_LEFT);

        // Table Section (Right)
        productTable = new TableView<>();

        TableColumn<Product, String> productColumn = new TableColumn<>("Pilih Produk");
        productColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Harga Produk");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> stockColumn = new TableColumn<>("Stok Produk");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productTable.getColumns().addAll(productColumn, priceColumn, stockColumn);
        productTable.setItems(ProductOperations.getAllProducts()); // Retrieve all products from DB
        productTable.setPrefHeight(400);

        // Add listener to populate fields when a product is selected
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Product selectedProduct = newSelection;
                productComboBox.setValue(selectedProduct.getName());
                priceField.setText(String.valueOf(selectedProduct.getPrice()));
                stockField.setText(String.valueOf(selectedProduct.getStock()));
            }
        });

        // Layout Configuration
        HBox layout = new HBox(20, formSection, productTable);
        layout.setPadding(new Insets(20));

        contentArea.getChildren().add(layout); // Add layout to the content area
    }

    private void clearFields() {
        productComboBox.setValue(null);
        priceField.clear();
        stockField.clear();
    }

    private void loadAdminAccountManagement(VBox contentArea) {
        contentArea.getChildren().clear();

        // Title
        Label title = new Label("Manajemen Akun Admin");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Table to display admin accounts
        TableView<AdminAccount> adminTable = new TableView<>();

        TableColumn<AdminAccount, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<AdminAccount, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<AdminAccount, String> emailColumn = new TableColumn<>("Email"); // New email column
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email")); // Set cell value factory for email

        adminTable.getColumns().addAll(usernameColumn, emailColumn, roleColumn);
        adminTable.setPrefHeight(300);

        // Populate table with data from AdminAccountOperations
        adminTable.setItems(AdminAccountOperations.getAllAdminAccounts());

        // Form to add/update admin accounts
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField emailField = new TextField(); // New email field
        emailField.setPromptText("Email");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.setPromptText("Role");
        roleComboBox.setItems(FXCollections.observableArrayList("Admin", "Super Admin"));

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");

        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        updateButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        clearButton.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");

        // Add button functionality
addButton.setOnAction(e -> {
    String username = usernameField.getText();
    String password = passwordField.getText();
    String email = emailField.getText(); // Get email
    String role = roleComboBox.getValue();
    
    // Assuming createdAt is set to the current timestamp
    Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && role != null) {
        AdminAccount newAdmin = new AdminAccount(
            0, // ID will be auto-generated by the database
            email, // Email from the input
            username,
            password,
            createdAt,
            role
        );

        // Call the method to add the admin account
        AdminAccountOperations.addAdminAccount(newAdmin);
    } else {
        System.out.println("Please fill in all fields!");
    }
});


        updateButton.setOnAction(e -> {
            AdminAccount selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
            if (selectedAdmin != null) {
                String newUsername = usernameField.getText();
                String newPassword = passwordField.getText();
                String newEmail = emailField.getText(); // Get new email
                String newRole = roleComboBox.getValue();

                if (!newUsername.isEmpty() && !newPassword.isEmpty() && !newEmail.isEmpty() && newRole != null) {
                    int adminId = selectedAdmin.getId();  // Get the selected admin's ID
                    AdminAccountOperations.updateAdminAccount(adminId, newUsername, newRole, newPassword, newEmail);  // Pass required parameters
                    adminTable.setItems(AdminAccountOperations.getAllAdminAccounts()); // Refresh table with updated data
                    clearFields(usernameField, passwordField, roleComboBox, emailField);  // Clear fields after updating
                } else {
                    System.out.println("Please fill in all fields.");
                }
            } else {
                System.out.println("Please select an admin to update.");
            }
        });

        deleteButton.setOnAction(e -> {
            AdminAccount selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
            if (selectedAdmin != null) {
                int adminId = selectedAdmin.getId();  // Get the selected admin's ID
                AdminAccountOperations.deleteAdminAccount(adminId);  // Pass the admin's ID for deletion
                adminTable.setItems(AdminAccountOperations.getAllAdminAccounts()); // Refresh table after deletion
            } else {
                System.out.println("Please select an admin to delete.");
            }
        });

        // Clear button functionality
        clearButton.setOnAction(e -> clearFields(usernameField, passwordField, roleComboBox, emailField));

        // Listener to populate fields when selecting a row
        adminTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                usernameField.setText(newSelection.getUsername());
                emailField.setText(newSelection.getEmail()); // Populate email field
                roleComboBox.setValue(newSelection.getRole());
            }
        });

        // Layout
        VBox formSection = new VBox(10, usernameField, passwordField, emailField, roleComboBox,
                new HBox(10, addButton, updateButton, deleteButton, clearButton));
        formSection.setPadding(new Insets(20));
        formSection.setStyle("-fx-background-color: #f8f8 f8; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        formSection.setAlignment(Pos.TOP_LEFT);

        contentArea.getChildren().addAll(title, new HBox(20, adminTable, formSection));
    }

    private void clearFields(TextField usernameField, TextField passwordField, ComboBox<String> roleComboBox, TextField emailField) {
        usernameField.clear();
        passwordField.clear();
        emailField.clear(); // Clear email field
        roleComboBox.setValue(null);
    }

    private void logout(Stage stage) {
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
            LoginView loginView = new LoginView(stage);
            loginView.showLoginView(); // Show the login view
        } else {
            // User chose Cancel, do nothing (stay logged in)
            System.out.println("Logout canceled.");
        }
    }

    private void toggleSidebar(VBox sidebar, Stage stage) {
        boolean isVisible = sidebar.isVisible();
        if (isVisible) {
            sidebar.setVisible(false);
            ((BorderPane) stage.getScene().getRoot()).setLeft(null);
        } else {
            sidebar.setVisible(true);
            ((BorderPane) stage.getScene().getRoot()).setLeft(sidebar);
        }
    }
}