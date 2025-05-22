package it.unicam.cs.ids.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BundledProductDTO extends DTO {
    private int quantityInBundle;
    private ProductDTO product;
    private double unitPriceInBundle;
}
