package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests;

import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import it.unicam.cs.ids.shared.kernel.enums.Currency;
import it.unicam.cs.ids.context.catalog.domain.model.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * CreateBundleRequest is used to create a new bundle of products.
 */
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreateBundleRequest extends DTO {
    private String name;
    private String description;
    private ProductCategory category;
    private List<CreateBundledProductRequest> bundledProducts;
    private Currency currency;
    private double discountPercentage;
    private List<String> tags;
    private int quantity;
    private long distributorId;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryTime;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates bundleLocation;
}
