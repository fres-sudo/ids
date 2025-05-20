package it.unicam.cs.ids.enums;

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

    private final String description;

    UnityOfMeasure(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
