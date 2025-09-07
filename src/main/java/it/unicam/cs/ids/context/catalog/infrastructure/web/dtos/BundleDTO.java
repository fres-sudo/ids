package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import it.unicam.cs.ids.context.catalog.domain.model.ProductCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * BundleDTO is used to represent a bundle of products in the system.
 * @see DTO
 */
@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BundleDTO extends DTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private ProductCategory category;
    private ApprovalStatus status;
    private List<BundledProductDTO> products;
    private Double discountPercentage;
    private List<String> tags;
    private Integer quantity;
    private CompanyDTO distributor;
    private Boolean availableForSale;
    private Boolean availableForShipping;
    private Integer estimatedDeliveryDays;
    private Double shippingCost;
    private String returnPolicy;
    private Coordinates bundleLocation;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
