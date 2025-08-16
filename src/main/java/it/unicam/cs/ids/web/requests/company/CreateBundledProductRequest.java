package it.unicam.cs.ids.web.requests.company;

import it.unicam.cs.ids.dto.DTO;
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
    private double pricePerUnit;
}
