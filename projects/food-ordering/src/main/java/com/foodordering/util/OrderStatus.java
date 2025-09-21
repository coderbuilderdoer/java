package com.foodordering.util;

/*
 * Enum representing different order statuses
 */
public enum OrderStatus {
    CREATED,
    PROCESSING,
    READY_FOR_PICKUP,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
