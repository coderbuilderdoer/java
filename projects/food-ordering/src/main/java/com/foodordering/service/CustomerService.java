package com.foodordering.service;

import java.util.HashMap;
import java.util.Map;
import com.foodordering.model.Customer;

/**
 * Service class for customer-related operations
 */
public class CustomerService {
    private Map<Integer, Customer> customers;

    public CustomerService() {
        this.customers = new HashMap<>();
    }

    // Add customer to service
    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    // Get customer by ID
    public Customer getCustomer(int customerId) {
        return customers.get(customerId);
    }

    // Get all customers
    public Map<Integer, Customer> getAllCustomers() {
        return customers;
    }

    // Remove customer by ID
    public void removeCustomer(int customerId) {
        customers.remove(customerId);
    }
}
