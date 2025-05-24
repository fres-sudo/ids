package it.unicam.cs.ids.enums;

import lombok.Getter;

/**
 * Various currencies with their descriptions.
 */
public enum Currency {
    EUR("Euro"),
    USD("United States Dollar"),
    GBP("British Pound Sterling"),
    JPY("Japanese Yen"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    CNY("Chinese Yuan Renminbi"),
    SEK("Swedish Krona"),
    NZD("New Zealand Dollar");

    /** Description of the currency. */
    @Getter private final String description;

    /**
     * Constructor for the {@link Currency} enum.
     * @param description The description of the currency.
     */
    Currency(String description) {
        this.description = description;
    }
}
