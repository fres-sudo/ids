package it.unicam.cs.ids.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bundled_products", schema = "ids_schema")
@Data
@NoArgsConstructor
public class BundledProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many BundledProducts can refer to one Product
    @JoinColumn(name = "product_id", nullable = false) // Foreign key to the Product table
    private Product product;

    @Column(nullable = false)
    private int quantityInBundle; // Quantity of this product within the bundle

    @Column(name = "price_per_unit", nullable = false)
    private double pricePerUnit; // Price per unit of the product in the bundle

    @ManyToOne // Many BundledProducts belong to one Bundle
    @JoinColumn(name = "bundle_id", nullable = false) // Foreign key to the Bundle table
    private Bundle bundle; // Back-reference to the parent Bundle
}