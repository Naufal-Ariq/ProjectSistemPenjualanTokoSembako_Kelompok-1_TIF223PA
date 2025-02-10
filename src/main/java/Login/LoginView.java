package Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Dashboard.AdminDashboard; 
import Dashboard.CustomerPortal;

public class LoginView {

    private Stage primaryStage;
    private Runnable onAdminLoginSuccess;
    private Runnable onUserLoginSuccess;

    // Constructor with Stage as parameter
    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Setters for callbacks
    public void setOnAdminLoginSuccess(Runnable onAdminLoginSuccess) {
        this.onAdminLoginSuccess = onAdminLoginSuccess;
    }

    public void setOnUserLoginSuccess(Runnable onUserLoginSuccess) {
        this.onUserLoginSuccess = onUserLoginSuccess; // Update to accept username
    }

    // Show login view
    public void showLoginView() {
        primaryStage.setTitle("Login Form");

        // Left Section - Image and Title
        ImageView imageView = new ImageView(new Image(getClass().getResource("/Images/Icon toko.jpeg").toExternalForm()));
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);

        Label titleLabel = new Label("Toko Sembako Management");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");

        VBox leftBox = new VBox(20, imageView, titleLabel);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setStyle("-fx-background-color: #d3d3d3; -fx-padding: 30px;");
        leftBox.setPrefWidth(350);

        // Right Section - Login Form
        Label signInLabel = new Label("Sign In");
        signInLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: red;");

        VBox formBox = new VBox(10, signInLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, messageLabel);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPadding(new Insets(20));
        formBox.setSpacing(10);

        // Action for Login Button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Username and password cannot be empty.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Validate Credentials
            if ("admin".equals(username) && "admin123".equals(password)) {
                messageLabel.setText("Login successful! (Admin)");
                messageLabel.setStyle("-fx-text-fill: green;");
                
                // Navigate to Admin Dashboard
                AdminDashboard adminDashboard = new AdminDashboard(primaryStage);
            } else if ("user".equals(username) && "user123".equals(password)) {
                messageLabel.setText("Login successful! (User )");
                messageLabel.setStyle("-fx-text-fill: green;");
               // Navigate to Customer Dashboard
                CustomerPortal customerportal = new CustomerPortal(primaryStage, username); // Create an instance of CustomerDashboard
            } else {
                messageLabel.setText("Invalid username or password.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Link to Registration view
        Label registerLink = new Label("Belum punya akun? Register di sini.");
        registerLink.setStyle("-fx-text-fill: blue; -fx-underline: true;");
        registerLink.setOnMouseClicked(event -> navigateToRegister()); // Navigate to Register

        // Combine Left and Right Sections
        HBox mainLayout = new HBox(leftBox, formBox);
        mainLayout.setSpacing(0);

        // Add the registration link to the form box
        formBox.getChildren().add(registerLink);

        Scene scene = new Scene(mainLayout, 600, 300);
        scene.getStylesheets().add(getClass().getResource("/CSS/login.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to navigate to the registration view
    private void navigateToRegister() {
        RegisterView registerView = new RegisterView(primaryStage); // Create an instance of RegisterView
        VBox registrationForm = registerView.getView(); // Get the registration view
        Scene registrationScene = new Scene(registrationForm, 400, 300); // Create a new scene for registration
        primaryStage.setScene(registrationScene); // Set the new scene
    }
}