package it.unicam.cs.ids.enums;


import lombok.Getter;

/**
 * Represents the various categories of products available on the platform.
 */
public enum ProductCategory {
    FRUITS                ("Fruits"),
    VEGETABLES            ("Vegetables"),
    GRAINS                ("Grains"),
    LEGUMES               ("Legumes"),
    NUTS_AND_SEED         ("Nuts and Seeds"),
    DAIRY                 ("Dairy"),
    MEAT                  ("Meat"),
    POULTRY               ("Poultry"),
    SEAFOOD               ("Seafood"),
    BAKERY                ("Baked Goods"),
    PASTA                 ("Pasta"),
    BEER                  ("Beer"),
    SPIRITS               ("Spirits"),
    HONEY                 ("Honey"),
    JAM_AND_PRESERVE      ("Jam and Preserves"),
    SAUCES_AND_CONDIMENTS ("Sauces and Condiments"),
    HERBS_AND_SPICES      ("Herbs and Spices"),
    PROCESSED_FOOD        ("Processed Food"),
    OTHER                 ("Other");


    /** Description of the product category. */
    @Getter private final String description;

    /**
     * Constructor for the {@link ProductCategory} enum.
     * @param description The description of the product category.
     */
    ProductCategory(String description) {
        this.description = description;
    }
}
