package it.unicam.cs.ids.context.catalog.domain.model;

/**
 * PurchaseStatus represents the current status of a purchase transaction.
 */
public enum PurchaseStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    REFUNDED
}