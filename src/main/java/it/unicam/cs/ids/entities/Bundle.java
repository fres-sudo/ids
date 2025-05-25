package it.unicam.cs.ids.entities;

import it.unicam.cs.ids.enums.ApprovalStatus;
import it.unicam.cs.ids.enums.Coordinates;
import it.unicam.cs.ids.enums.ProductCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * Bundle represents a collection of products that can are sold together.
 * Bundles can be created only by a {@link Company}, by grouping multiple {@link Product} into a single entity.
 * These products can be from different Companies,
 * but the Bundle itself is associated with a single Company that creates it.
 *
 */
@Data @EqualsAndHashCode(callSuper = true)
public class Bundle extends BaseEntity {
    private String description;
    private ProductCategory category;
    private ApprovalStatus status;
    private List<Product> products;
    private double price;
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
    private Company creator;
}
