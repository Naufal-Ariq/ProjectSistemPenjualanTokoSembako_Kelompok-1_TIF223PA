package Login;

import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Dashboard.CustomerPortal;

public class RegisterView {

    private Stage primaryStage;
    private LoginOperations loginOperations;

    // Constructor for RegisterView
    public RegisterView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginOperations = new LoginOperations();
    }

    // Create registration view
    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // Title label
        Label titleLabel = new Label("Register");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-pref-width: 300px;");

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-pref-width: 300px;");
        
        // Email field
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-pref-width: 300px;");

        // Register button
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Link to go back to login
        Label loginLink = new Label("Sudah punya akun? Login di sini.");
        loginLink.setStyle("-fx-text-fill: blue; -fx-underline: true;");
        loginLink.setOnMouseClicked(event -> navigateToLogin());

        // Handle register button action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            
            // Validate input
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields!");
                alert.showAndWait();
                return;
            }
            
            try {
                // Save user to database
                loginOperations.registerUser (username, password, email); // Save user to database
                
                // Show success message
                showSuccess("Registration successful! You can now log in.");
                
                // Navigate back to login view
                navigateToLogin();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Registration failed: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        // Add elements to VBox
        root.getChildren().addAll(titleLabel, usernameField, passwordField, emailField, registerButton, loginLink);
        return root;
    }

    // Show success message
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Show error message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigate back to login view
    private void navigateToLogin() {
        LoginView loginView = new LoginView(primaryStage); // Create an instance of LoginView
        loginView.showLoginView(); // Show the login view
    }
}