package com.foodordering.model;

import com.foodordering.util.OrderStatus;

/*
 * Pickup order class (inherits from Order)
 */
public class PickupOrder extends Order{
    public PickupOrder(Customer customer, Restaurant restaurant) {
        super(customer, restaurant);
    }

    @Override
    public void processOrder() {
        super.processOrder();
        setStatus(OrderStatus.READY_FOR_PICKUP);
        System.out.println("Order #" + orderId + " is ready for pickup at " + restaurant.getName());
    }

    @Override
    public String toString() {
        return String.format(
            "┌─  Pickup Order #%-3d ───────────┐%n" +
            " Customer: %-22s %n" +
            " Restaurant: %-20s %n" +
            " Items: %-2d | Total: $%-10.2f %n" +
            " Status: %-24s %n" +
            " Pickup at: %-22s %n" +
            "└─────────────────────────────────┘",
            orderId,
            customer != null ? customer.getName() : "null",
            restaurant != null ? restaurant.getName() : "null",
            items.size(),
            calculateBill(),
            status,
            restaurant != null ? restaurant.getName() : "null"
        );
    }
}
