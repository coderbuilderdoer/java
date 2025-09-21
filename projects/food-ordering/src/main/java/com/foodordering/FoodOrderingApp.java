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
 * Main application class for the Food Ordering System
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
    }

    // Demonstrate the food ordering system
    public void demonstrate() {
        try {
            // Initialize database
            DatabaseManager.initializeDatabase();

            // Initialize sample data
            initializeData();

            System.out.println("=== FOOD ORDERING SYSTEM DEMONSTRATION ===");

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
            System.out.println("\n=== PLACING ORDERS ===");

            // Regular customer places pickup order
            Order pickupOrder = new PickupOrder(regularCustomer, pizzaRestaurant);
            pickupOrder.addOrderItem(pizzaRestaurant.getMenuItem(101));
            orderService.placeOrder(pickupOrder);

            // Premium customer places delivery order
            Order deliveryOrder = new DeliveryOrder(premiumCustomer, chineseRestaurant, "123 Main St");
            deliveryOrder.addOrderItem(chineseRestaurant.getMenuItem(301));
            deliveryOrder.addOrderItem(chineseRestaurant.getMenuItem(302));
            orderService.placeOrder(deliveryOrder);

            // Process orders concurrently using threads
            System.out.println("\n=== PROCESSING ORDERS CONCURRENTLY ===");
            List<Order> orders = orderService.getAllOrders();

            for (Order order : orders) {
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
            System.out.println("\n=== ALL ORDERS ===");
            for (Order order : orderService.getAllOrders()) {
                System.out.println(order);
            }

            // Demonstrate exception handling
            System.out.println("\n=== DEMONSTRATING EXCEPTION HANDLING ===");
            try {
                restaurantService.getRestaurant(999); // Non-existent restaurant
            } catch (RestaurantNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Create an order that will fail payment (for demonstration)
            try {
                Customer poorCustomer = new RegularCustomer(3, "Poor Student", "student@email.com", 50.0);
                customerService.addCustomer(poorCustomer);

                Order expensiveOrder = new PickupOrder(poorCustomer, pizzaRestaurant);
                expensiveOrder.addOrderItem(pizzaRestaurant.getMenuItem(101));
                expensiveOrder.addOrderItem(pizzaRestaurant.getMenuItem(102));

                orderService.placeOrder(expensiveOrder);
            } catch (PaymentFailedException e) {
                System.out.println("Payment Error: " + e.getMessage());
            }

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
