package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.shared.kernel.enums.Currency;

import java.time.LocalDateTime;

public interface Purchasable {
    void updateQuantity(int purchasedQuantity);
    void validatePurchase(int requestedQuantity);
    double computeTotalPrice(int quantity);
    double getUnitPrice();
    double getShippingCost();
    Currency getCurrency();
    LocalDateTime computeDeliveryDate();
}
