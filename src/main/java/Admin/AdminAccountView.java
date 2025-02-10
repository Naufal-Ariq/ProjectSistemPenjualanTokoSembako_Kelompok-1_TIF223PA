package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class AdminAccountView {

    private TableView<AdminAccount> adminTable;
    private TextField usernameField, passwordField, roleField, emailField;
    private VBox layout;

    public AdminAccountView() {
        adminTable = new TableView<>();
        usernameField = new TextField();
        passwordField = new PasswordField();
        roleField = new TextField();
        emailField = new TextField();

        setupTableView();
        setupForm();
        setupLayout();
    }

    private void setupTableView() {
        TableColumn<AdminAccount, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<AdminAccount, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<AdminAccount, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<AdminAccount, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<AdminAccount, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        adminTable.getColumns().addAll(idColumn, nameColumn, usernameColumn, roleColumn, emailColumn);
    }

    private void setupForm() {
        Label titleLabel = new Label("Admin Account Management");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label roleLabel = new Label("Role:");
        Label emailLabel = new Label("Email:");

        Button addButton = new Button("Add Admin");
        addButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleField.getText().trim();
            String email = emailField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || role.isEmpty() || email.isEmpty()) {
                showError("All fields must be filled.");
                return;
            }

            AdminAccount newAdmin = new AdminAccount(0, email, username, password, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()), role);
            AdminAccountOperations.addAdminAccount(newAdmin);

            // Refresh table
            ObservableList<AdminAccount> admins = AdminAccountOperations.getAllAdminAccounts();
            adminTable.setItems(admins);

            usernameField.clear();
            passwordField.clear();
            roleField.clear();
            emailField.clear();
        });

        VBox formBox = new VBox(10, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, roleLabel, roleField, emailLabel, emailField, addButton);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPadding(new Insets(20));
        formBox.setSpacing(10);

        layout.getChildren().add(formBox);
    }

    private void setupLayout() {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        layout.getChildren().add(adminTable);
    }

    public VBox getView() {
        return layout;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
