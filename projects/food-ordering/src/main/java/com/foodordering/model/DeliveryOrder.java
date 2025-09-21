package com.foodordering.model;

import com.foodordering.util.OrderStatus;

public class DeliveryOrder extends Order{
    private String deliveryAddress;
    private static final double DELIVERY_CHARGE = 10.00;

    public DeliveryOrder(Customer customer, Restaurant restaurant, String deliveryAddress) {
        super(customer, restaurant);
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
        return "Delivery " + super.toString() + " - Address: " + deliveryAddress;
    }
}
