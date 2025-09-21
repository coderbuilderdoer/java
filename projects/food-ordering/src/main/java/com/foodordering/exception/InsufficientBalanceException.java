package com.foodordering.exception;

/**
 * Custom exception for when a customer has insufficient balance.
 * Demonstrates custom exception handling in Java.
 */
public class InsufficientBalanceException extends Exception {

    /**
     * Default constructor for InsufficientBalanceException.
     */
    public InsufficientBalanceException() {
        super();
    }

    /**
     * Constructor with a custom message.
     * @param message the detail message explaining the insufficient balance
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }

    /**
     * Constructor with a cause.
     * @param cause the underlying cause of this exception
     */
    public InsufficientBalanceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with both a custom message and a cause.
     * @param message the detail message explaining the insufficient balance
     * @param cause the underlying cause of this exception
     */
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
