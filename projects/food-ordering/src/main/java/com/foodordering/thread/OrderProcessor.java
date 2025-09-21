package com.foodordering.thread;

import com.foodordering.model.Order;
import com.foodordering.service.OrderService;

public class OrderProcessor extends Thread{
    private OrderService orderService;
    private Order order;

    public OrderProcessor(OrderService orderService, Order order) {
        this.orderService = orderService;
        this.order = order;
    }

    @Override
    public void run() {
        try {
            System.out.println("Processing order #" + order.getOrderId() + " on thread: " + Thread.currentThread().getName());
            // Simulate processing time
            Thread.sleep(2000);
            // Process the order
            order.processOrder();
            System.out.println("Order #" + order.getOrderId() + " processed successfully");
        } catch(InterruptedException e) {
            System.err.println("Order processing interrupted for order #" + order.getOrderId());
            Thread.currentThread().interrupt();
        }
    }
}
