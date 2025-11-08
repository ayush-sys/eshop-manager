package com.example.eshop.enums;

public enum AppEnums {
    // Lookup / search
    FOUND,
    NOT_FOUND,

    // Lifecycle / catalog management
    DRAFT,
    CREATED,
    PENDING_APPROVAL,
    APPROVED,
    REJECTED,
    ACTIVE,
    INACTIVE,
    ADDED_TO_CATALOG,
    REMOVED_FROM_CATALOG,
    ARCHIVED,
    DELETED,
    CATEGORY_NOT_FOUND,

    // Inventory / availability
    IN_STOCK,
    OUT_OF_STOCK,
    LOW_STOCK,
    NO_UNITS_AVAILABLE,
    UNITS_ALLOCATED,
    RESERVED,
    BACKORDER,
    BACKORDERABLE,
    PREORDER,
    PENDING_RESTOCK,
    RESTOCKED,
    DISCONTINUED,

    // Pricing / promotions
    PRICE_UPDATED,
    PRICE_ERROR,
    ON_SALE,

    // Order / fulfillment
    ALLOCATED,
    SHIPPABLE,
    NON_SHIPPABLE,
    RETURNED,
    REFUNDED,

    // Processing / sync / errors
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED,
}