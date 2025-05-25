package it.unicam.cs.ids.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;


//FIXME: see in VPP
@Data @EqualsAndHashCode(callSuper = false)
public class BundledProductDTO extends DTO {
    private int quantityInBundle;
    private ProductDTO product;
    private double unitPriceInBundle;
}
