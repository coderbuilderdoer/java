package com.foodordering.model;

import com.foodordering.exception.PaymentFailedException;

/**
 * Base Customer class representing a customer in the food ordering system.
 * This class demonstrates Java fundamentals: encapsulation, constructors, getters/setters.
 * Will be extended by RegularCustomer and PremiumCustomer to demonstrate inheritance.
 */
public class Customer {
    // Customer attributes (id, name, email, phone, address) as mentioned in README
    private int id;
    private String name;
    private String email;
    private double balance;

    // Default constructor
    public Customer() {
    }

    // Parameterized constructor
    public Customer(int id, String name, String email, double balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }


    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method to place order(to be overridden by subclass)
    public double placeOrder(Order order) throws PaymentFailedException {
        System.out.println("Placing order for: " + name);
        double total = order.calculateBill();

        if(balance < total) {
            throw new PaymentFailedException("Insufficient balance for order: $" + total);
        }

        balance -= total;
        return total;
    }

    // Override toString() method
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name = '" + name + '\'' +
                ", email = '" + email + '\'' +
                ", balance = '" + balance + '\'' +
                '}';
    }
}
