package com.foodordering.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.foodordering.database.DatabaseManager;
import com.foodordering.model.Customer;

/**
 * Service class for customer-related operations with database integration
 */
public class CustomerService {
    private Map<Integer, Customer> customers;

    public CustomerService() {
        this.customers = new HashMap<>();
        loadCustomersFromDatabase();
    }

    // Load customers from database on startup
    private void loadCustomersFromDatabase() {
        List<Customer> customerList = DatabaseManager.loadAllCustomers();
        for (Customer customer : customerList) {
            customers.put(customer.getId(), customer);
        }
        System.out.println("Loaded " + customerList.size() + " customers from database");
    }

    // Add customer to service and database
    public void addCustomer(Customer customer) {
        // Save to database first
        DatabaseManager.saveCustomer(customer);

        // Then add to in-memory collection
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
        // Note: In a real application, you'd also delete from database
    }

    // Update customer balance in both memory and database
    public void updateCustomerBalance(int customerId, double newBalance) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            customer.setBalance(newBalance);
            DatabaseManager.updateCustomerBalance(customerId, newBalance);
        }
    }
}
