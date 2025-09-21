package com.foodordering.exception;

/**
 * Custom exception for when a restaurant is not found in the system.
 * Demonstrates custom exception handling in Java.
 */
public class RestaurantNotFoundException extends Exception {

    /**
     * Default constructor for RestaurantNotFoundException.
     */
    public RestaurantNotFoundException() {
        super();
    }

    /**
     * Constructor with a custom message.
     * @param message the detail message explaining why the restaurant was not found
     */
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with a cause.
     * @param cause the underlying cause of this exception
     */
    public RestaurantNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with both a custom message and a cause.
     * @param message the detail message explaining why the restaurant was not found
     * @param cause the underlying cause of this exception
     */
    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
