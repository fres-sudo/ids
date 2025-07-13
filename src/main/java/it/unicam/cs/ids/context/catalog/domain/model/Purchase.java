package it.unicam.cs.ids.context.catalog.domain.model;

import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.shared.kernel.enums.Currency;
import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Purchase represents a completed purchase transaction for either a Product or Bundle.
 */
@Entity
@Table(name = "purchases", schema = "ids_schema")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Purchase extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id")
    private Bundle bundle;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.EUR;

    @Column(name = "purchase_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @Column(name = "purchase_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status = PurchaseStatus.COMPLETED;

    @Column(name = "delivery_address", columnDefinition = "TEXT")
    private String deliveryAddress;

    @Column(name = "shipping_cost")
    private double shippingCost;

    @Column(name = "estimated_delivery_date")
    @Temporal(TemporalType.DATE)
    private Date estimatedDeliveryDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @PrePersist
    protected void onCreate() {
        if (purchaseDate == null) {
            purchaseDate = new Date();
        }
    }

    public boolean isProductPurchase() {
        return product != null;
    }

    public boolean isBundlePurchase() {
        return bundle != null;
    }
}