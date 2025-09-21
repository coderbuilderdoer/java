package com.foodordering.exception;

/**
 * Custom exception for when a payment transaction fails.
 * Demonstrates custom exception handling in Java.
 */
public class PaymentFailedException extends Exception {

    /**
     * Default constructor for PaymentFailedException.
     */
    public PaymentFailedException() {
        super();
    }

    /**
     * Constructor with a custom message.
     * @param message the detail message explaining why the payment failed
     */
    public PaymentFailedException(String message) {
        super(message);
    }

    /**
     * Constructor with a cause.
     * @param cause the underlying cause of this exception
     */
    public PaymentFailedException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with both a custom message and a cause.
     * @param message the detail message explaining why the payment failed
     * @param cause the underlying cause of this exception
     */
    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
