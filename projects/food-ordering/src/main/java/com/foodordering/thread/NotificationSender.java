package com.foodordering.thread;
import com.foodordering.model.Order;

public class NotificationSender extends Thread{
    private Order order;

    public NotificationSender(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        try{
            System.out.println("Sending notification for order #" + order.getOrderId() + " on thread: " + Thread.currentThread().getName());
            Thread.sleep(1000); // Simulate notification sending time

            // Send notification
            System.out.println("Notification sent for order #" + order.getOrderId() +
                    ": Your order status is now '" + order.getStatus() + "'");

        } catch(InterruptedException e) {
            System.err.println("Notification sending interrupted for order #" + order.getOrderId());
            Thread.currentThread().interrupt();
        }
    }
}
