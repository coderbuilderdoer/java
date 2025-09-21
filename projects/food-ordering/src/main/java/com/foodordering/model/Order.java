package com.foodordering.model;

import java.util.ArrayList;
import java.util.List;
import com.foodordering.util.OrderStatus;

/**
 * Base Order class representing an order in the food ordering system.
 * Will be extended by PickupOrder and DeliveryOrder to demonstrate inheritance and polymorphism.
 */
public class Order {
    // Order attributes as mentioned in README (orderId, customer, restaurant, items, status)
    private static int nextOrderId = 1;

    protected int orderId;
    protected Customer customer;
    protected Restaurant restaurant;
    protected List<MenuItem> items;
    protected OrderStatus status;


    // Default constructor
    public Order() {
    }

    // Parameterized constructor
    public Order(Customer customer, Restaurant restaurant) {
        this.orderId = nextOrderId++;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    // Calculate total bill
    public double calculateBill() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    // Process order (to be overridden by subclasses)
    public void processOrder() {
        status = OrderStatus.PROCESSING;
        System.out.println("Order #" + orderId + " is being processed");
    }

    // Getter methods
    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<MenuItem> getOrderItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    // Setter methods
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Method to update order status
    public void updateOrderStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    // Method to add items to order
    public void addOrderItem(MenuItem item) {
        if (item != null) {
            this.items.add(item);
            System.out.println(calculateBill()); // Recalculate total when items are added
        }
    }

    // Method to remove items from order
    public void removeOrderItem(MenuItem item) {
        if (item != null) {
            this.items.remove(item);
            System.out.println(calculateBill()); // Recalculate total when items are removed
        }
    }

    // Override toString() method
    @Override
    public String toString() {
        return "Order{" +
                "orderId = " + orderId +
                ", customer = " + (customer != null ? customer.getName() : "null") +
                ", restaurant = " + (restaurant != null ? restaurant.getName() : "null") +
                ", orderItems = " + items.size() +
                ", totalAmount = " + calculateBill() +
                ", status = '" + status +
                '}';
    }
}
