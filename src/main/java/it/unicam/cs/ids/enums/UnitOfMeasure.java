package it.unicam.cs.ids.enums;


import lombok.Getter;

/**
 * Represents the various units of measure used in the system.
 */
public enum UnitOfMeasure {
    MG      ("Milligram"),
    G       ("Gram"),
    KG      ("Kilogram"),
    TON     ("Metric Ton"),
    ML      ("Milliliter"),
    CL      ("Centiliter"),
    L       ("Liter"),
    PCS     ("Piece"),       // Individual items
    BAG     ("Bag"),         // Grains, dried goods
    BOX     ("Box"),         // Fruits, vegetables
    BOTTLE  ("Bottle"),      // Oils, sauces, drinks
    JAR     ("Jar"),         // Honey, preserves
    CAN     ("Can"),         // Canned goods
    PACK    ("Pack"),        // Pre-packaged items
    SACK    ("Sack"),        // Potatoes, grains
    LOAF    ("Loaf"),        // Bread
    BAR     ("Bar"),         // Chocolate, soap
    PALETTE ("Palette"),     // Shipping/storage
    BALE    ("Bale");        // Hay, straw


    /** Description of the unit of measure */
    @Getter private final String description;

    /**
     * Constructor for the {@link UnitOfMeasure} enum.
     * @param description The description of the unit of measure.
     */
    UnitOfMeasure(String description) {
        this.description = description;
    }
}
