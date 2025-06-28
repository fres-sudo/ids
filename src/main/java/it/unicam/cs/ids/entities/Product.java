package it.unicam.cs.ids.entities;

import it.unicam.cs.ids.enums.ApprovalStatus;
import it.unicam.cs.ids.enums.Currency;
import it.unicam.cs.ids.enums.ProductCategory;
import it.unicam.cs.ids.enums.UnitOfMeasure;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class Product extends BaseEntity {

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
    private UnitOfMeasure unityOfMeasure;

    @Column(name = "currency", length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.EUR;

    @ElementCollection
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
}