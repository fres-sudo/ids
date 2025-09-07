package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.shared.kernel.enums.Currency;

import java.time.LocalDateTime;

public interface Purchasable {
    void updateQuantity(int purchasedQuantity);
    void validatePurchase(int requestedQuantity);
    Double computeTotalPrice(int quantity);
    Double getUnitPrice();
    Double getShippingCost();
    Currency getCurrency();
    LocalDateTime computeDeliveryDate();
}
