package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests;

import it.unicam.cs.ids.shared.application.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * CreateBundledProductRequest is used to create a new bundled product in a bundle.
 */
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreateBundledProductRequest extends DTO {
    private long productId;
    private long bundleId;
    private int quantityInBundle;
    private double unitPriceInBundle;
}
