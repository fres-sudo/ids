package it.unicam.cs.ids.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BundledProductDTO extends DTO {
    private int quantityInBundle;
    private ProductDTO product;
    private double pricePerUnit;
}
