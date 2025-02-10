package com.mycompany.projectpenjualantokosembako;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Konfigurasi koneksi database
    private static final String URL = "jdbc:mysql://localhost:3306/toko_sembako?useSSL=false&serverTimezone=UTC"; // Ganti 'toko_sembako' dengan nama database Anda
    private static final String USER = "root"; // Ganti 'root' dengan username database Anda
    private static final String PASSWORD = ""; // Ganti dengan password database Anda jika ada

    private static Connection connection;

    // Memuat driver JDBC hanya sekali saat kelas pertama kali dipanggil
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    // Mendapatkan koneksi ke database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Membuat koneksi ke database
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to establish database connection.");
                e.printStackTrace();
                throw e;  // Melempar kembali exception untuk penanganan lebih lanjut
            }
        }
        return connection;
    }

    // Menutup koneksi database jika sudah tidak digunakan
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close database connection.");
                e.printStackTrace();
            }
        }
    }
}
