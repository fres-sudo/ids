package it.unicam.cs.ids.entities;

import it.unicam.cs.ids.enums.ApprovalStatus;
import it.unicam.cs.ids.enums.Coordinates;
import it.unicam.cs.ids.enums.ProductCategory;
import it.unicam.cs.ids.enums.UnitOfMeasure;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * Product represents a single sellable item on the platform, which can be sold by a {@link Company}.
 * Products can be sold individually or as part of a {@link Bundle}, if a product is part of a bundle,
 * it becomes a {@link BundledProduct} of that bundle.
 */
@Data @EqualsAndHashCode(callSuper=true)
public class Product extends BaseEntity{
    private String description;
    private Date productionDate;
    private ProductCategory category;
    private ApprovalStatus status;
    private int quantity;
    private double pricePerQuantity;
    private UnitOfMeasure unityOfMeasure;
    private Currency currency;
    private List<String> tags;
    private String producerId;
    private String cultivationMethod;
    private Date expirationDate;
    private Date harvestDate;
    private boolean availableForSale;
    private boolean availableForShipping;
    private int estimatedDeliveryTime;
    private double shippingCost;
    private String returnPolicy;
    private Coordinates productLocation;
    private List<Company> distributors;
    private List<Company> transformers;
    private List<Certificate> certificates;
    private List<String> imageUrls;
    private Company creator;
}
