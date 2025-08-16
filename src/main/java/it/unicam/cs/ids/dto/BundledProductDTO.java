package it.unicam.cs.ids.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO representing a product that is part of a bundle.
 * It contains the quantity of the product in the bundle,
 * the product details, and the price per unit.
 */
@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BundledProductDTO extends DTO {
    private int quantityInBundle;
    private ProductDTO product;
    private double pricePerUnit;
}
