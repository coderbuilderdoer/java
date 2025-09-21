package com.foodordering.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.foodordering.model.Customer;
import com.foodordering.model.DeliveryOrder;
import com.foodordering.model.MenuItem;
import com.foodordering.model.Order;
import com.foodordering.model.PickupOrder;
import com.foodordering.model.PremiumCustomer;
import com.foodordering.model.RegularCustomer;
import com.foodordering.model.Restaurant;
import com.foodordering.util.CuisineType;
import com.foodordering.util.OrderStatus;

/**
 * Database connection and management class with real database operations
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
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

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

    // ========== CUSTOMER DATABASE OPERATIONS ==========

    // Save customer to database
    public static void saveCustomer(Customer customer) {
        String sql = "INSERT INTO customers (id, name, email, balance, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setDouble(4, customer.getBalance());
            pstmt.setString(5, customer.getClass().getSimpleName());

            pstmt.executeUpdate();
            System.out.println("> Customer \"" + customer.getName() + "\" saved to database");

        } catch (SQLException e) {
            System.err.println("Error saving customer: " + e.getMessage());
        }
    }

    // Load all customers from database
    public static List<Customer> loadAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");
                String type = rs.getString("type");

                Customer customer;
                if ("PremiumCustomer".equals(type)) {
                    customer = new PremiumCustomer(id, name, email, balance);
                } else {
                    customer = new RegularCustomer(id, name, email, balance);
                }

                customers.add(customer);
            }

        } catch (SQLException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }

        return customers;
    }

    // Update customer balance
    public static void updateCustomerBalance(int customerId, double newBalance) {
        String sql = "UPDATE customers SET balance = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setInt(2, customerId);

            pstmt.executeUpdate();
            System.out.println("Customer balance updated for ID: " + customerId);

        } catch (SQLException e) {
            System.err.println("Error updating customer balance: " + e.getMessage());
        }
    }

    // ========== RESTAURANT DATABASE OPERATIONS ==========

    // Save restaurant to database
    public static void saveRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO restaurants (id, name, rating) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, restaurant.getId());
            pstmt.setString(2, restaurant.getName());
            pstmt.setDouble(3, restaurant.getRating());

            pstmt.executeUpdate();
            System.out.println("> Restaurant \"" + restaurant.getName() + "\" saved to database");

        } catch (SQLException e) {
            System.err.println("Error saving restaurant: " + e.getMessage());
        }
    }

    // Load all restaurants from database
    public static List<Restaurant> loadAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double rating = rs.getDouble("rating");

                Restaurant restaurant = new Restaurant(id, name, rating);
                restaurants.add(restaurant);
            }

        } catch (SQLException e) {
            System.err.println("Error loading restaurants: " + e.getMessage());
        }

        return restaurants;
    }

    // ========== MENU ITEM DATABASE OPERATIONS ==========

    // Save menu item to database
    public static void saveMenuItem(MenuItem menuItem, int restaurantId) {
        String sql = "INSERT INTO menu_items (id, restaurant_id, name, price, description, cuisine_type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, menuItem.getId());
            pstmt.setInt(2, restaurantId);
            pstmt.setString(3, menuItem.getName());
            pstmt.setDouble(4, menuItem.getPrice());
            pstmt.setString(5, menuItem.getDescription());
            pstmt.setString(6, menuItem.getCuisineType().toString());

            pstmt.executeUpdate();
            System.out.println("> Menu item \"" + menuItem.getName() + "\" saved to database");

        } catch (SQLException e) {
            System.err.println("Error saving menu item: " + e.getMessage());
        }
    }

    // Load menu items for a restaurant
    public static List<MenuItem> loadMenuItemsForRestaurant(int restaurantId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE restaurant_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                CuisineType cuisineType = CuisineType.valueOf(rs.getString("cuisine_type"));

                MenuItem menuItem = new MenuItem(id, name, price, description, cuisineType);
                menuItems.add(menuItem);
            }

        } catch (SQLException e) {
            System.err.println("Error loading menu items: " + e.getMessage());
        }

        return menuItems;
    }

    // ========== ORDER DATABASE OPERATIONS ==========

    // Save order to database
    public static void saveOrder(Order order) {
        String sql = "INSERT INTO orders (id, customer_id, restaurant_id, total_amount, status, type, delivery_address) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order.getOrderId());
            pstmt.setInt(2, order.getCustomer().getId());
            pstmt.setInt(3, order.getRestaurant().getId());
            pstmt.setDouble(4, order.calculateBill());
            pstmt.setString(5, order.getStatus().toString());
            pstmt.setString(6, order.getClass().getSimpleName());

            // Handle delivery address for delivery orders
            if (order instanceof DeliveryOrder) {
                pstmt.setString(7, ((DeliveryOrder) order).getDeliveryAddress());
            } else {
                pstmt.setString(7, null);
            }

            pstmt.executeUpdate();

            // Save order items
            saveOrderItems(order);

            System.out.println("> Order #" + order.getOrderId() + " saved to database");

        } catch (SQLException e) {
            System.err.println("Error saving order: " + e.getMessage());
        }
    }

    // Save order items to database
    private static void saveOrderItems(Order order) {
        String sql = "INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (MenuItem item : order.getOrderItems()) {
                pstmt.setInt(1, order.getOrderId());
                pstmt.setInt(2, item.getId());
                pstmt.setInt(3, 1); // Default quantity of 1
                pstmt.addBatch();
            }

            pstmt.executeBatch();

        } catch (SQLException e) {
            System.err.println("Error saving order items: " + e.getMessage());
        }
    }

    // Load all orders from database
    public static List<Order> loadAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int orderId = rs.getInt("id");
                int customerId = rs.getInt("customer_id");
                int restaurantId = rs.getInt("restaurant_id");
                String status = rs.getString("status");
                String type = rs.getString("type");
                String deliveryAddress = rs.getString("delivery_address");

                // Load customer and restaurant (simplified - in real app, you'd have service references)
                Customer customer = loadCustomerById(customerId);
                Restaurant restaurant = loadRestaurantById(restaurantId);

                Order order;
                if ("DeliveryOrder".equals(type)) {
                    order = new DeliveryOrder(customer, restaurant, deliveryAddress);
                } else {
                    order = new PickupOrder(customer, restaurant);
                }

                order.setOrderId(orderId);
                order.setStatus(OrderStatus.valueOf(status));

                // Load order items
                loadOrderItems(order);

                orders.add(order);
            }

        } catch (SQLException e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }

        return orders;
    }

    // Load customer by ID
    private static Customer loadCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");
                String type = rs.getString("type");

                if ("PremiumCustomer".equals(type)) {
                    return new PremiumCustomer(customerId, name, email, balance);
                } else {
                    return new RegularCustomer(customerId, name, email, balance);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading customer: " + e.getMessage());
        }

        return null;
    }

    // Load restaurant by ID
    private static Restaurant loadRestaurantById(int restaurantId) {
        String sql = "SELECT * FROM restaurants WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                double rating = rs.getDouble("rating");

                Restaurant restaurant = new Restaurant(restaurantId, name, rating);

                // Load menu items for this restaurant
                List<MenuItem> menuItems = loadMenuItemsForRestaurant(restaurantId);
                for (MenuItem item : menuItems) {
                    restaurant.addMenuItem(item);
                }

                return restaurant;
            }

        } catch (SQLException e) {
            System.err.println("Error loading restaurant: " + e.getMessage());
        }

        return null;
    }

    // Load order items for an order
    private static void loadOrderItems(Order order) {
        String sql = "SELECT mi.* FROM order_items oi JOIN menu_items mi ON oi.menu_item_id = mi.id WHERE oi.order_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order.getOrderId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                CuisineType cuisineType = CuisineType.valueOf(rs.getString("cuisine_type"));

                MenuItem menuItem = new MenuItem(id, name, price, description, cuisineType);
                order.addOrderItem(menuItem);
            }

        } catch (SQLException e) {
            System.err.println("Error loading order items: " + e.getMessage());
        }
    }

    // Update order status
    public static void updateOrderStatus(int orderId, OrderStatus newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus.toString());
            pstmt.setInt(2, orderId);

            pstmt.executeUpdate();
            System.out.println("Order #" + orderId + " status updated to: " + newStatus);

        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
        }
    }

    // Clear all data (for testing purposes)
    public static void clearAllData() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM order_items");
            stmt.executeUpdate("DELETE FROM orders");
            stmt.executeUpdate("DELETE FROM menu_items");
            stmt.executeUpdate("DELETE FROM restaurants");
            stmt.executeUpdate("DELETE FROM customers");

            System.out.println("All data cleared from database");

        } catch (SQLException e) {
            System.err.println("Error clearing data: " + e.getMessage());
        }
    }
}
