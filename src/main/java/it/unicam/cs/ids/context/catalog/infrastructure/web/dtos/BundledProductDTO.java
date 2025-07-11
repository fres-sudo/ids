package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos;

import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BundledProductDTO extends DTO {
    private int quantityInBundle;
    private ProductDTO product;
    private double unitPriceInBundle;
}
