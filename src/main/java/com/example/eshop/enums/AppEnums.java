package com.example.eshop.enums;

import lombok.Getter;

/**
 * Application-wide enumerations for consistent status and operation identifiers.
 */
@Getter
public enum AppEnums {

    // Lookup / search
    FOUND("Record found successfully"),
    NOT_FOUND("Record not found"),

    // Lifecycle / catalog management
    DRAFT("Draft created"),
    CREATED("Created successfully"),
    UPDATED("Updated successfully"),
    PENDING_APPROVAL("Pending approval"),
    APPROVED("Approved successfully"),
    REJECTED("Rejected"),
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    ADDED_TO_CATALOG("Added to catalog"),
    REMOVED_FROM_CATALOG("Removed from catalog"),
    ARCHIVED("Archived"),
    DELETED("Deleted successfully"),
    CATEGORY_NOT_FOUND("Category not found"),

    // Inventory / availability
    IN_STOCK("In stock"),
    OUT_OF_STOCK("Out of stock"),
    LOW_STOCK("Low stock"),
    NO_UNITS_AVAILABLE("No units available"),
    UNITS_ALLOCATED("Units allocated"),
    RESERVED("Reserved"),
    BACKORDER("Backorder placed"),
    BACKORDERABLE("Available for backorder"),
    PREORDER("Preorder available"),
    PENDING_RESTOCK("Pending restock"),
    RESTOCKED("Restocked"),
    DISCONTINUED("Discontinued"),

    // Pricing / promotions
    PRICE_UPDATED("Price updated successfully"),
    PRICE_ERROR("Error updating price"),
    ON_SALE("On sale"),

    // Order / fulfillment
    ALLOCATED("Allocated"),
    SHIPPABLE("Ready to ship"),
    NON_SHIPPABLE("Non-shippable"),
    RETURNED("Returned"),
    REFUNDED("Refunded"),

    // Processing / sync / errors
    PENDING("Request pending"),
    PROCESSING("Request in processing"),
    COMPLETED("Transaction completed"),
    SUCCESS("Operation successful"),
    ERROR("Error occurred"),
    FAILED("Operation failed"),;

    private final String message;

    AppEnums(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
