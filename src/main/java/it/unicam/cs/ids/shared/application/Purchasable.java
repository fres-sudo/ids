package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.services.AuthService;
import it.unicam.cs.ids.shared.kernel.enums.Currency;

import java.time.LocalDateTime;

/**
 * Interface representing a purchasable item in the system.
 * It provides methods to update quantity, validate purchase, compute total price,
 * get unit price, shipping cost, currency, and delivery date.
 */
public interface Purchasable {
    /**
     * Updates the quantity of the purchasable item based on the purchased quantity.
     *
     * @param purchasedQuantity the quantity that has been purchased
     */
    void updateQuantity(int purchasedQuantity);

    /**
     * Validates the requested purchase quantity against the available stock.
     *
     * @param requestedQuantity the quantity requested for purchase
     * @throws IllegalArgumentException if the requested quantity exceeds available stock
     */
    void validatePurchase(int requestedQuantity);

    /**
     * Computes the total price for a given quantity of the purchasable item.
     *
     * @param quantity the quantity for which to compute the total price
     * @return the total price for the specified quantity
     */
    double computeTotalPrice(int quantity);

    /**
     * Gets the unit price of the purchasable item.
     *
     * @return the unit price of the item
     */
    double getUnitPrice();

    /**
     * Gets the shipping cost associated with the purchasable item.
     *
     * @return the shipping cost
     */
    double getShippingCost();

    /**
     * Gets the currency in which the purchasable item is priced.
     *
     * @return the currency of the item
     */
    Currency getCurrency();

    /**
     * Computes the delivery date based on the current time and any business logic defined for delivery.
     *
     * @return the computed delivery date
     */
    LocalDateTime computeDeliveryDate();
}
