package it.unicam.cs.ids.models;

import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.shared.application.Purchasable;
import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;
import it.unicam.cs.ids.shared.kernel.enums.Currency;
import it.unicam.cs.ids.shared.kernel.enums.ProductCategory;
import it.unicam.cs.ids.shared.kernel.enums.UnitOfMeasure;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList; // Use concrete list types
import java.util.Date;
import java.util.List;

/**
 * Product represents a single sellable item on the platform, which can be sold by a {@link Company}.
 */
@Entity
@Table(name = "products", schema = "ids_schema")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity implements Approvable, Purchasable {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "production_date")
    @Temporal(TemporalType.DATE)
    private Date productionDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.DRAFT;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "price_per_quantity", nullable = false)
    private double pricePerQuantity;

    @Column(name = "unit_of_measure", nullable = false)
    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.EUR;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "producer_company_id", nullable = false)
    private Company producer;

    @Column(name = "cultivation_method", length = 255)
    private String cultivationMethod;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @Column(name = "harvest_date")
    @Temporal(TemporalType.DATE)
    private Date harvestDate;

    @Column(name = "available_for_sale", nullable = false)
    private boolean availableForSale;

    @Column(name = "available_for_shipping", nullable = false)
    private boolean availableForShipping;

    @Column(name = "estimated_delivery_days")
    private int estimatedDeliveryDays;

    @Column(name = "shipping_cost")
    private double shippingCost;

    @Column(name = "return_policy", columnDefinition = "TEXT")
    private String returnPolicy;

    @Embedded
    private Coordinates productLocation;

    @ManyToMany
    @JoinTable(
            name = "product_distributors",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> distributors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_transformers",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> transformers = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Certificate> certificates = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_image_urls", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "creator_company_id", nullable = false)
    private Company creator;

    @Override
    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.status = approvalStatus;
    }

    @Override
    public void updateQuantity(int purchasedQuantity) {
        this.quantity -= purchasedQuantity;
        if (this.quantity <= 0) {
            this.availableForSale = false;
        }
    }

    @Override
    public void validatePurchase(int requestedQuantity) {
        if (!this.availableForSale) {
            throw new IllegalArgumentException("This Product not available for sale");
        }
        if (this.quantity < requestedQuantity) {
            throw new IllegalArgumentException("Insufficient quantity in stock");
        }
    }

    @Override
    public double computeTotalPrice(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        double totalPrice = this.pricePerQuantity * quantity;
        return totalPrice + this.shippingCost;
    }

    @Override
    public double getUnitPrice() {
        return this.pricePerQuantity;
    }

    @Override
    public LocalDateTime computeDeliveryDate() {
        if (this.estimatedDeliveryDays <= 0) {
            return null; // No delivery date if estimated days are not set
        }
        return java.time.LocalDateTime.now().plusDays(this.estimatedDeliveryDays);
    }
}