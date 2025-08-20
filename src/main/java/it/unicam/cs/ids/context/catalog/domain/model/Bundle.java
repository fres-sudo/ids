package it.unicam.cs.ids.context.catalog.domain.model;

import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.shared.application.Purchasable;
import it.unicam.cs.ids.shared.kernel.enums.Currency;
import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Bundle represents a collection of {@link Product}s sold together.
 */
@Entity
@Table(name = "bundles", schema = "ids_schema")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Bundle extends BaseEntity implements Approvable, Purchasable {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    // orphanRemoval = true: If a BundledProduct is removed from the 'products' list, it's also deleted from the DB
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BundledProduct> products = new ArrayList<>();

    @Column(name = "discount_percentage")
    private double discountPercentage;

    @ElementCollection
    @CollectionTable(name = "bundle_tags", joinColumns = @JoinColumn(name = "bundle_id"))
    @Column(name = "tag")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<String> tags = new ArrayList<>();

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "distributor_company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company distributor; // Changed from long distributorId to Company entity

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
    private Coordinates bundleLocation;

    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency;

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
            throw new IllegalArgumentException("This Bundle not available for sale");
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
        double totalPrice = 0;
        for (BundledProduct bundledProduct : this.products) {
            totalPrice += bundledProduct.getProduct().getPricePerQuantity() * bundledProduct.getQuantityInBundle();
        }
        totalPrice *= quantity;
        if (this.discountPercentage > 0) {
            totalPrice -= (totalPrice * this.discountPercentage / 100);
        }
        return totalPrice + this.shippingCost;
    }

    @Override
    public double getUnitPrice() {
        double totalPrice = 0;
        for (BundledProduct bundledProduct : this.products) {
            totalPrice += bundledProduct.getProduct().getPricePerQuantity() * bundledProduct.getQuantityInBundle();
        }
        return totalPrice;
    }

    @Override
    public LocalDateTime computeDeliveryDate() {
        if (this.estimatedDeliveryDays <= 0) {
            return null; // No delivery date if estimated days are not set
        }
        return java.time.LocalDateTime.now().plusDays(this.estimatedDeliveryDays);
    }
}