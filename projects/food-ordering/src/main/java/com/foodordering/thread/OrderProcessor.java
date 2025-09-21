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
            System.out.println(" [ORDER] Processing order #" + order.getOrderId() + " (Thread: " + Thread.currentThread().getName() + ")");
            // Simulate processing time
            Thread.sleep(2000);
            // Process the order
            order.processOrder();
            System.out.println("✅ [ORDER] Order #" + order.getOrderId() + " completed successfully");
        } catch(InterruptedException e) {
            System.err.println("❌ [ORDER] Processing interrupted for order #" + order.getOrderId());
            Thread.currentThread().interrupt();
        }
    }
}
