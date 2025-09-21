package com.foodordering.model;

import com.foodordering.util.CuisineType;

/**
 * MenuItem class representing individual food items in a restaurant's menu.
 */
public class MenuItem {
    // Menu item attributes (id, name, description, price, category, availability)
    private int id;
    private String name;
    private double price;
    private String description;
    private CuisineType cuisineType;

    // Default constructor
    public MenuItem() {
    }

    // Parameterized constructor
    public MenuItem(int id, String name, double price,  String description, CuisineType cuisineType) {
        this();
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.cuisineType = cuisineType;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    // Override toString() method
    @Override
    public String toString() {
        return "MenuItem{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", price = " + price +
                ", description = '" + description + '\'' +
                ", cuisine type = '" + cuisineType +
                '}';
    }
}
