package Admin;

import com.mycompany.projectpenjualantokosembako.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminAccountOperations {

    // CREATE: Tambahkan Akun Admin Baru
    public static void addAdminAccount(AdminAccount admin) {
        String query = "INSERT INTO admin_accounts (username, password, role, created_at, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setString(3, admin.getRole());
            stmt.setTimestamp(4, admin.getCreatedAt());
            stmt.setString(5, admin.getEmail());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin account added: " + admin.getUsername());
            }
        } catch (SQLException e) {
            System.err.println("Error adding admin account to database.");
            e.printStackTrace();
        }
    }

    // READ: Ambil semua akun admin
    public static ObservableList<AdminAccount> getAllAdminAccounts() {
        ObservableList<AdminAccount> adminList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM admin_accounts";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AdminAccount admin = new AdminAccount(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at"),
                        rs.getString("role")
                );
                adminList.add(admin);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching admin accounts from database.");
            e.printStackTrace();
        }

        return adminList;
    }

    // UPDATE: Update akun admin
    public static void updateAdminAccount(int id, String username, String password, String role, String email) {
        String sql = "UPDATE admin_accounts SET username = ?, password = ?, role = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, email);
            stmt.setInt(5, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin account updated: " + username);
            } else {
                System.out.println("No admin account found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error updating admin account in database.");
            e.printStackTrace();
        }
    }

    // DELETE: Hapus akun admin
    public static void deleteAdminAccount(int id) {
        String sql = "DELETE FROM admin_accounts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin account deleted with ID: " + id);
            } else {
                System.out.println("No admin account found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting admin account from database.");
            e.printStackTrace();
        }
    }
}
