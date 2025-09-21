package com.foodordering.service;

import java.util.HashMap;
import java.util.Map;
import com.foodordering.exception.RestaurantNotFoundException;
import com.foodordering.model.Restaurant;

/**
 * Service class for restaurant-related operations
 */
public class RestaurantService {
    private Map<Integer, Restaurant> restaurants;

    public RestaurantService() {
        this.restaurants = new HashMap<>();
    }

    // Add restaurant to service
    public void addRestaurant(Restaurant restaurant) {
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
    }
}
