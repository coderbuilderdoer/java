package com.foodordering.service;

import java.util.ArrayList;
import java.util.List;
import com.foodordering.exception.PaymentFailedException;
import com.foodordering.model.Order;

/**
 * Service class for order-related operations
 */
public class OrderService {
    private List<Order> orders;

    public OrderService() {
        this.orders = new ArrayList<>();
    }

    // Place a new order
    public void placeOrder(Order order) throws PaymentFailedException {
        double amount = order.getCustomer().placeOrder(order);
        order.processOrder();
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
    public void updateOrderStatus(int orderId, String status) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                // the OrderStatus enum would be used in a real application
                System.out.println("Order #" + orderId + " status updated to: " + status);
                return;
            }
        }
        System.out.println("Order #" + orderId + " not found");
    }
}
