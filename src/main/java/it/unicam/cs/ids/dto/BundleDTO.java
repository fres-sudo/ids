package it.unicam.cs.ids.dto;

import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import it.unicam.cs.ids.shared.kernel.enums.ProductCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * BundleDTO is used to represent a bundle of products in the system.
 * @see DTO
 */
@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BundleDTO extends DTO {
    private long id;
    private String name;
    private String description;
    private ProductCategory category;
    private ApprovalStatus status;
    private List<BundledProductDTO> products;
    private double discountPercentage;
    private List<String> tags;
    private int quantity;
    private CompanyDTO distributor;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryDays;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates bundleLocation;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
