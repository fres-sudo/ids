package it.unicam.cs.ids.enums;


import lombok.Getter;

/**
 * Represents the various units of measure used in the system.
 */
public enum UnityOfMeasure {
    KG("Kilogram"),
    G("Gram"),
    L("Liter"),
    ML("Milliliter"),
    M("Meter"),
    CM("Centimeter"),
    MM("Millimeter"),
    PCS("Pieces"),
    BOX("Box"),
    BOTTLE("Bottle"),
    JAR("Jar"),
    CAN("Can"),
    PACK("Pack");

    /** Description of the unit of measure */
    @Getter private final String description;

    /**
     * Constructor for the {@link UnityOfMeasure} enum.
     * @param description The description of the unit of measure.
     */
    UnityOfMeasure(String description) {
        this.description = description;
    }
}
