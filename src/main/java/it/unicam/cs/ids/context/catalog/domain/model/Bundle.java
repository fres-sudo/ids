// src/main/java/it/unicam/cs/ids/entities/Bundle.java
package it.unicam.cs.ids.context.catalog.domain.model;

import it.unicam.cs.ids.context.company.domain.models.Company;
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
public class Bundle extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    // orphanRemoval = true: If a BundledProduct is removed from the 'products' list, it's also deleted from the DB
    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BundledProduct> products = new ArrayList<>();

    @Column(nullable = false)
    private double price;

    @Column(name = "discount_percentage")
    private double discountPercentage;

    @ElementCollection
    @CollectionTable(name = "bundle_tags", joinColumns = @JoinColumn(name = "bundle_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "distributor_company_id")
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

    @ManyToOne
    @JoinColumn(name = "creator_company_id", nullable = false)
    private Company creator;

    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}