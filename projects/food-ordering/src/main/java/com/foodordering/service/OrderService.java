package com.foodordering.service;

import java.util.ArrayList;
import java.util.List;
import com.foodordering.database.DatabaseManager;
import com.foodordering.exception.PaymentFailedException;
import com.foodordering.model.Order;
import com.foodordering.util.OrderStatus;

/**
 * Service class for order-related operations with database integration
 */
public class OrderService {
    private List<Order> orders;

    public OrderService() {
        this.orders = new ArrayList<>();
        loadOrdersFromDatabase();
    }

    // Load orders from database on startup
    private void loadOrdersFromDatabase() {
        List<Order> orderList = DatabaseManager.loadAllOrders();
        orders.addAll(orderList);
        System.out.println("Loaded " + orderList.size() + " orders from database");
    }

    // Place a new order
    public void placeOrder(Order order) throws PaymentFailedException {
        // Process payment
        double amount = order.getCustomer().placeOrder(order);

        // Process the order
        order.processOrder();

        // Save to database
        DatabaseManager.saveOrder(order);

        // Add to in-memory collection
        orders.add(order);

        System.out.println("Order placed successfully! Amount: $" + amount);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orders;
    }

    // Get orders by customer ID
    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomer().getId() == customerId) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    // Get orders by restaurant ID
    public List<Order> getOrdersByRestaurant(int restaurantId) {
        List<Order> restaurantOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getRestaurant().getId() == restaurantId) {
                restaurantOrders.add(order);
            }
        }
        return restaurantOrders;
    }

    // Update order status
    public void updateOrderStatus(int orderId, OrderStatus status) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                order.setStatus(status);
                DatabaseManager.updateOrderStatus(orderId, status);
                System.out.println("Order #" + orderId + " status updated to: " + status);
                return;
            }
        }
        System.out.println("Order #" + orderId + " not found");
    }
}
