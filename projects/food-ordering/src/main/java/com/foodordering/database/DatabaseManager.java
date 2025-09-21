package com.foodordering.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Database connection and management class
 */
public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/food_ordering";
    private static final String USER = "root";
    private static final String PASSWORD = "R00tPwd";

    // Get database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Initialize database tables
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            // Create customers table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customers (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100), " +
                    "balance DECIMAL(10,2), " +
                    "type VARCHAR(20))");

            // Create restaurants table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS restaurants (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "rating DECIMAL(3,2))");

            // Create menu_items table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS menu_items (" +
                    "id INT PRIMARY KEY, " +
                    "restaurant_id INT, " +
                    "name VARCHAR(100), " +
                    "price DECIMAL(10,2), " +
                    "description TEXT, " +
                    "cuisine_type VARCHAR(50), " +
                    "FOREIGN KEY (restaurant_id) REFERENCES restaurants(id))");

            // Create orders table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders (" +
                    "id INT PRIMARY KEY, " +
                    "customer_id INT, " +
                    "restaurant_id INT, " +
                    "total_amount DECIMAL(10,2), " +
                    "status VARCHAR(50), " +
                    "type VARCHAR(20), " +
                    "delivery_address VARCHAR(200), " +
                    "FOREIGN KEY (customer_id) REFERENCES customers(id), " +
                    "FOREIGN KEY (restaurant_id) REFERENCES restaurants(id))");

            // Create order_items table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS order_items (" +
                    "order_id INT, " +
                    "menu_item_id INT, " +
                    "quantity INT, " +
                    "PRIMARY KEY (order_id, menu_item_id), " +
                    "FOREIGN KEY (order_id) REFERENCES orders(id), " +
                    "FOREIGN KEY (menu_item_id) REFERENCES menu_items(id))");

            System.out.println("Database initialized successfully");

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    // Save order to database
    public static void saveOrder(com.foodordering.model.Order order) {
        // In a real application, we would implement this method to save order details to the database
        System.out.println("Order #" + order.getOrderId() + " saved to database");
    }
}
