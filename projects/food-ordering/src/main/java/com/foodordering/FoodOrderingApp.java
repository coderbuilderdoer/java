package com.foodordering;

import java.util.List;
import com.foodordering.database.DatabaseManager;
import com.foodordering.exception.PaymentFailedException;
import com.foodordering.exception.RestaurantNotFoundException;
import com.foodordering.model.Customer;
import com.foodordering.model.DeliveryOrder;
import com.foodordering.model.MenuItem;
import com.foodordering.model.Order;
import com.foodordering.model.PickupOrder;
import com.foodordering.model.PremiumCustomer;
import com.foodordering.model.RegularCustomer;
import com.foodordering.model.Restaurant;
import com.foodordering.service.CustomerService;
import com.foodordering.service.OrderService;
import com.foodordering.service.RestaurantService;
import com.foodordering.thread.NotificationSender;
import com.foodordering.thread.OrderProcessor;
import com.foodordering.util.CuisineType;

/**
 * Main application class for the Food Ordering System with Database Integration
 */
public class FoodOrderingApp {
    private RestaurantService restaurantService;
    private CustomerService customerService;
    private OrderService orderService;

    public FoodOrderingApp() {
        this.restaurantService = new RestaurantService();
        this.customerService = new CustomerService();
        this.orderService = new OrderService();
    }

    // Initialize sample data
    public void initializeData() {
        System.out.println("\n=== INITIALIZING SAMPLE DATA ===\n");

        // Create restaurants and menu items
        Restaurant restaurant1 = new Restaurant(1, "Pizza Palace", 4.5);
        restaurant1.addMenuItem(new MenuItem(101, "Margherita Pizza", 299, "Classic cheese pizza", CuisineType.ITALIAN));
        restaurant1.addMenuItem(new MenuItem(102, "Pepperoni Pizza", 399, "Pepperoni with extra cheese", CuisineType.ITALIAN));
        restaurantService.addRestaurant(restaurant1);

        Restaurant restaurant2 = new Restaurant(2, "Burger King", 4.2);
        restaurant2.addMenuItem(new MenuItem(201, "Whopper", 199, "Signature burger", CuisineType.AMERICAN));
        restaurant2.addMenuItem(new MenuItem(202, "Cheese Burger", 149, "Cheesy delight", CuisineType.AMERICAN));
        restaurantService.addRestaurant(restaurant2);

        Restaurant restaurant3 = new Restaurant(3, "Chinese Wok", 4.8);
        restaurant3.addMenuItem(new MenuItem(301, "Hakka Noodles", 249, "Spicy noodles", CuisineType.CHINESE));
        restaurant3.addMenuItem(new MenuItem(302, "Manchurian", 299, "Vegetable balls in sauce", CuisineType.CHINESE));
        restaurantService.addRestaurant(restaurant3);

        // Create customers
        Customer regularCustomer = new RegularCustomer(1, "John Doe", "john@email.com", 1000.0);
        Customer premiumCustomer = new PremiumCustomer(2, "Jane Smith", "jane@email.com", 1500.0);
        customerService.addCustomer(regularCustomer);
        customerService.addCustomer(premiumCustomer);

        System.out.println("Sample data initialization completed!");
    }

    // Demonstrate the food ordering system
    public void demonstrate() {
        try {
            // Initialize database
            System.out.println("\n=== INITIALIZING DATABASE ===\n");
            DatabaseManager.initializeDatabase();

            // Clear existing data for clean demonstration
            System.out.println("\n=== CLEARING EXISTING DATA ===\n");
            DatabaseManager.clearAllData();

            // Initialize sample data
            initializeData();

            System.out.println("\n=== FOOD ORDERING SYSTEM DEMONSTRATION ===");

            // Get customers and restaurants
            Customer regularCustomer = customerService.getCustomer(1);
            Customer premiumCustomer = customerService.getCustomer(2);
            Restaurant pizzaRestaurant = restaurantService.getRestaurant(1);
            Restaurant chineseRestaurant = restaurantService.getRestaurant(3);

            // Display customers and restaurants
            System.out.println("\n=== CUSTOMERS ===");
            System.out.println(regularCustomer);
            System.out.println(premiumCustomer);

            System.out.println("\n=== RESTAURANTS ===");
            System.out.println(pizzaRestaurant);
            System.out.println(chineseRestaurant);

            // Place orders
            System.out.println("\n=== PLACING ORDERS ===\n");

            // Regular customer places pickup order
            System.out.println("Order 1: Regular Customer Pickup Order");
            Order pickupOrder = new PickupOrder(regularCustomer, pizzaRestaurant);
            pickupOrder.addOrderItem(pizzaRestaurant.getMenuItem(101));
            System.out.println("  - Added item: " + pizzaRestaurant.getMenuItem(101).getName() + " ($" + String.format("%.2f", pizzaRestaurant.getMenuItem(101).getPrice()) + ")");
            System.out.println("  - Total: $" + String.format("%.2f", pickupOrder.calculateBill()));
            orderService.placeOrder(pickupOrder);
            System.out.println("  - Order placed successfully!\n");

            // Premium customer places delivery order
            System.out.println("Order 2: Premium Customer Delivery Order");
            Order deliveryOrder = new DeliveryOrder(premiumCustomer, chineseRestaurant, "123 Main St");
            deliveryOrder.addOrderItem(chineseRestaurant.getMenuItem(301));
            deliveryOrder.addOrderItem(chineseRestaurant.getMenuItem(302));
            System.out.println("  - Added item: " + chineseRestaurant.getMenuItem(301).getName() + " ($" + String.format("%.2f", chineseRestaurant.getMenuItem(301).getPrice()) + ")");
            System.out.println("  - Added item: " + chineseRestaurant.getMenuItem(302).getName() + " ($" + String.format("%.2f", chineseRestaurant.getMenuItem(302).getPrice()) + ")");
            System.out.println("  - Subtotal: $" + String.format("%.2f", deliveryOrder.calculateBill() - 10.00));
            System.out.println("  - Delivery fee: $10.00");
            System.out.println("  - Total: $" + String.format("%.2f", deliveryOrder.calculateBill()));
            orderService.placeOrder(deliveryOrder);
            System.out.println("  - Order placed successfully!\n");

            // Process orders concurrently using threads
            System.out.println("\n=== PROCESSING ORDERS CONCURRENTLY ===");
            List<Order> orders = orderService.getAllOrders();

            for (Order order : orders) {
                System.out.println("\n--- Processing Order #" + order.getOrderId() + " ---");

                // Process order in separate thread
                OrderProcessor orderProcessor = new OrderProcessor(orderService, order);
                orderProcessor.start();

                // Send notification in separate thread
                NotificationSender notificationSender = new NotificationSender(order);
                notificationSender.start();

                // Wait for threads to complete (in real app, we'd use thread pooling)
                try {
                    orderProcessor.join();
                    notificationSender.join();
                } catch (InterruptedException e) {
                    System.err.println("Thread interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }

            // Display all orders
            System.out.println("\n=== ALL ORDERS ===\n");
            for (Order order : orderService.getAllOrders()) {
                System.out.println(order);
            }

            System.out.println("\n=== DEMONSTRATING EXCEPTION HANDLING ===");

            // Test 1: Restaurant not found
            System.out.println("\n🔍 Test 1: Looking for non-existent restaurant...");
            try {
                restaurantService.getRestaurant(999); // Non-existent restaurant
            } catch (RestaurantNotFoundException e) {
                System.out.println("❌ Restaurant Error: " + e.getMessage());
            }

            // Test 2: Payment failure
            System.out.println("\n💳 Test 2: Attempting order with insufficient funds...");
            try {
                Customer studentCustomer = new RegularCustomer(3, "Student", "student@email.com", 90.0);
                customerService.addCustomer(studentCustomer);
                System.out.println("✅ Customer 'Student' added successfully");

                Order expensiveOrder = new PickupOrder(studentCustomer, pizzaRestaurant);
                expensiveOrder.addOrderItem(pizzaRestaurant.getMenuItem(101));
                expensiveOrder.addOrderItem(pizzaRestaurant.getMenuItem(102));
                System.out.println("📋 Order created with 2 items (Total: $" + String.format("%.2f", expensiveOrder.calculateBill()) + ")");

                orderService.placeOrder(expensiveOrder);
            } catch (PaymentFailedException e) {
                System.out.println("❌ Payment Error: " + e.getMessage());
            }

            System.out.println();

            // Demonstrate database persistence
            // System.out.println("\n=== DATABASE PERSISTENCE DEMONSTRATION ===");
            // System.out.println("All data has been saved to the database!");
            // System.out.println("You can now check your MySQL database to see the records.");
            // System.out.println("Run the application again to see data loaded from database.");

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main method to run the Food Ordering System demonstration
     */
    public static void main(String[] args) {
        FoodOrderingApp app = new FoodOrderingApp();
        app.demonstrate();
    }
}
