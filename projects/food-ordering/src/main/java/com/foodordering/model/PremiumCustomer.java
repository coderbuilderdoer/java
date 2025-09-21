package com.foodordering.model;

import com.foodordering.exception.PaymentFailedException;

/**
 * PremiumCustomer extends Customer to demonstrate inheritance and polymorphism.
 * Premium customers get discounts on their orders.
 */
public class PremiumCustomer extends Customer {
    // Add premium customer specific attributes (discount percentage, membership level)
    private static final double DISCOUNT_RATE = 0.1; // 10% discount

    public PremiumCustomer(int id, String name, String email, double balance) {
        super(id,  name, email, balance);
    }

    @Override
    public double placeOrder(Order order) throws PaymentFailedException {
        System.out.println("Placing order for premium customer: " + getName());
        double total = order.calculateBill();
        double discount = total * DISCOUNT_RATE;
        double finalAmount = total - discount;

        if(getBalance() < finalAmount) {
            throw new PaymentFailedException("Insufficient balance for order: $" + finalAmount);
        }

        setBalance(getBalance() - finalAmount);
        System.out.println("Premium discount applied: -$" + String.format("%.2f", discount));

        return finalAmount;
    }

    @Override
    public String toString() {
        return String.format(
            "┌─ Premium Customer ──────────────┐%n" +
            "│ ID: %-4d                        │%n" +
            "│ Name: %-25s │%n" +
            "│ Email: %-24s │%n" +
            "│ Balance: $%-20.2f  │%n" +
            "│ Discount: 10%%                   │%n" +
            "└─────────────────────────────────┘",
            getId(), getName(), getEmail(), getBalance()
        );
    }
}
