package com.foodordering.model;

import com.foodordering.util.OrderStatus;

public class DeliveryOrder extends Order{
    private String deliveryAddress;
    private static final double DELIVERY_CHARGE = 10.00;

    public DeliveryOrder(Customer customer, Restaurant restaurant, String deliveryAddress) {
        super(customer, restaurant);
        this.deliveryAddress = deliveryAddress;
    }

    // Getter for delivery address
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    // Setter for delivery address
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public double calculateBill() {
        double total = super.calculateBill();
        return total + DELIVERY_CHARGE;
    }

    @Override
    public void processOrder() {
        super.processOrder();
        setStatus(OrderStatus.OUT_FOR_DELIVERY);
        System.out.println("Order #" + orderId + " is out for delivery to " + deliveryAddress);
    }

    @Override
    public String toString() {
        return String.format(
            "┌─  Delivery Order #%-3d ─────────┐%n" +
            " Customer: %-22s %n" +
            " Restaurant: %-20s %n" +
            " Items: %-2d | Total: $%-10.2f %n" +
            " Status: %-24s %n" +
            " Delivery to: %-20s %n" +
            "└─────────────────────────────────┘",
            orderId,
            customer != null ? customer.getName() : "null",
            restaurant != null ? restaurant.getName() : "null",
            items.size(),
            calculateBill(),
            status,
            deliveryAddress
        );
    }
}
