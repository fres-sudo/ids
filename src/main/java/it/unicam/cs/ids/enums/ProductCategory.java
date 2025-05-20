package it.unicam.cs.ids.enums;

public enum ProductCategory {
    FRUITS("Fruits"),
    VEGETABLES("Vegetables"),
    GRAINS("Grains"),
    LEGUMES("Legumes"),
    NUTS_AND_SEED("Nuts and Seeds"),
    DAIRY("Dairy"),
    MEAT("Meat"),
    PULTRY("Poultry"),
    SEAFOOD("Seafood"),
    BACKERY("Baked Goods"),
    PASTA("Pasta"),
    BEER("Beer"),
    SPIRITS("Spirits"),
    HONEY("Honey"),
    JAM_AND_PRESERVE("Jam and Preserves"),
    SAUCES_AND_CONDIMENTS("Sauces and Condiments"),
    HERBS_AND_SPICES("Herbs and Spices"),
    PROCESSED_FOOD("Processed Food");

    private final String description;

    ProductCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
