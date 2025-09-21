package com.foodordering.model;

/**
 * RegularCustomer extends Customer to demonstrate inheritance.
 * Regular customers don't get any special discounts.
 */
public class RegularCustomer extends Customer {
    public RegularCustomer(int id, String name, String email, double balance) {
        super(id, name, email, balance);
    }

    @Override
    public String toString() {
        return String.format(
            "┌─ Regular Customer ──────────────┐%n" +
            "│ ID: %-4d                        │%n" +
            "│ Name: %-25s │%n" +
            "│ Email: %-24s │%n" +
            "│ Balance: $%-20.2f  │%n" +
            "└─────────────────────────────────┘",
            getId(), getName(), getEmail(), getBalance()
        );
    }
}
