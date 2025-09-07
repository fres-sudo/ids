package it.unicam.cs.ids.context.catalog.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bundled_products", schema = "ids_schema")
@Data
@NoArgsConstructor
public class BundledProduct extends BaseEntity {

    @ManyToOne // Many BundledProducts can refer to one Product
    @JoinColumn(name = "product_id", nullable = false) // Foreign key to the Product table
    private Product product;

    @ManyToOne
    @JoinColumn(name = "bundle_id", nullable = false) // Foreign key to the Bundle table
    @JsonIgnore
    private Bundle bundle_id;

    @Column(nullable = false)
    private int quantityInBundle; // Quantity of this product within the bundle

    @Column(name = "price_per_unit", nullable = false)
    private double pricePerUnit; // Price per unit of the product in the bundle
}