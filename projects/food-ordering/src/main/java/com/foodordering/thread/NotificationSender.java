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
            System.out.println("📱 [NOTIFY] Sending notification for order #" + order.getOrderId() + " (Thread: " + Thread.currentThread().getName() + ")");
            Thread.sleep(1000); // Simulate notification sending time

            // Send notification
            System.out.println(" [NOTIFY] Order #" + order.getOrderId() + " status: " + order.getStatus());

        } catch(InterruptedException e) {
            System.err.println("❌ [NOTIFY] Notification interrupted for order #" + order.getOrderId());
            Thread.currentThread().interrupt();
        }
    }
}
