package com.foodordering.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Restaurant class representing a restaurant in the food ordering system.
 * Demonstrates encapsulation and composition (restaurant has menu items).
 */
public class Restaurant {
    // Restaurant attributes (id, name, address, rating, cuisine type)
    private int id;
    private String name;
    private double rating;
    private Map<Integer, MenuItem> menu; // HashMap for menu items

    // Default constructor
    public Restaurant() {
    }

    // Parameterized constructor
    public Restaurant(int id, String name, double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.menu = new HashMap<>();
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }
    // Get menu item by ID
    public MenuItem getMenuItem(int itemId) {
        return menu.get(itemId);
    }

    // Get menu items
    public Map<Integer, MenuItem> getMenuItems() {
        return menu;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    // Methods to add/remove menu items
    public void addMenuItem(MenuItem item) {
        if (item != null) {
            this.menu.put(item.getId(), item);
        }
    }

    public void removeMenuItem(MenuItem item) {
        if (item != null) {
            this.menu.remove(item.getId());
        }
    }

    // Override toString() method
    @Override
    public String toString() {
        return String.format(
            "┌─ Restaurant ────────────────────┐%n" +
            "│ ID: %-4d                        │%n" +
            "│ Name: %-25s │%n" +
            "│ Rating: ⭐ %-20.1f │%n" +
            "└─────────────────────────────────┘",
            id, name, rating
        );
    }
}
