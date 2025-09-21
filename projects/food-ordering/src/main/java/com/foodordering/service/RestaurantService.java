package com.foodordering.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.foodordering.database.DatabaseManager;
import com.foodordering.exception.RestaurantNotFoundException;
import com.foodordering.model.Restaurant;

/**
 * Service class for restaurant-related operations with database integration
 */
public class RestaurantService {
    private Map<Integer, Restaurant> restaurants;

    public RestaurantService() {
        this.restaurants = new HashMap<>();
        loadRestaurantsFromDatabase();
    }

    // Load restaurants from database on startup
    private void loadRestaurantsFromDatabase() {
        List<Restaurant> restaurantList = DatabaseManager.loadAllRestaurants();
        for (Restaurant restaurant : restaurantList) {
            restaurants.put(restaurant.getId(), restaurant);
        }
        System.out.println("Loaded " + restaurantList.size() + " restaurants from database");
    }

    // Add restaurant to service and database
    public void addRestaurant(Restaurant restaurant) {
        // Save restaurant to database first
        DatabaseManager.saveRestaurant(restaurant);

        // Save menu items to database
        for (com.foodordering.model.MenuItem menuItem : restaurant.getMenuItems().values()) {
            DatabaseManager.saveMenuItem(menuItem, restaurant.getId());
        }

        // Then add to in-memory collection
        restaurants.put(restaurant.getId(), restaurant);
    }

    // Get restaurant by ID
    public Restaurant getRestaurant(int restaurantId) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("Restaurant with ID " + restaurantId + " not found");
        }
        return restaurant;
    }

    // Get all restaurants
    public Map<Integer, Restaurant> getAllRestaurants() {
        return restaurants;
    }

    // Remove restaurant by ID
    public void removeRestaurant(int restaurantId) {
        restaurants.remove(restaurantId);
        // Note: In a real application, you'd also delete from database
    }
}
