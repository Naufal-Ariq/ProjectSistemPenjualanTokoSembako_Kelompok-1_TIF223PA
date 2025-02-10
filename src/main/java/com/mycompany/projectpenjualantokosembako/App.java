package com.mycompany.projectpenjualantokosembako;

import Dashboard.AdminDashboard;
import Login.LoginView;
import Product.ProductOperations;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Dashboard.CustomerPortal;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create and show the login view
        Stage loginStage = new Stage();
        LoginView loginView = new LoginView(loginStage); // Pass the loginStage to the constructor

        // Set up login success callback for admin
        loginView.setOnAdminLoginSuccess(() -> {
            // Close the login stage
            loginStage.close();

            // Show the Admin Dashboard for admin users
            AdminDashboard adminDashboard = new AdminDashboard(primaryStage);
        });

        // Set up login success callback for regular users
        loginView.setOnUserLoginSuccess(() -> {
            // Close the login stage
            loginStage.close();

           // Show the Customer Dashboard for regular users
    String username = "user"; // Replace with the actual username if needed
    CustomerPortal customerPortal = new CustomerPortal(primaryStage, username); // Pass the username
    customerPortal.showDashboard(); // Call the method to display the portal
});
        // Display the login view
        loginView.showLoginView(); // Call to show the login view
    }

    public static void main(String[] args) {
        launch(args);
    }
}