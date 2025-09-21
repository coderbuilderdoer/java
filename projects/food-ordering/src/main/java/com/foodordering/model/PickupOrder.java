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
        return "Pickup " + super.toString();
    }
}
