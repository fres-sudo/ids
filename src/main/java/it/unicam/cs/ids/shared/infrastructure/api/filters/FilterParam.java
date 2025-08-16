package it.unicam.cs.ids.shared.infrastructure.api.filters;

import it.unicam.cs.ids.shared.kernel.enums.ProductCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * FilterParam is a filter class used to specify search criteria for products.
 * It includes fields for searching by name, tags, availability, price range, and product categories
 */
@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class FilterParam implements Serializable {
    private String searchBy;
    private List<String> tags;
    private boolean available;
    private boolean availableForShipping;
    private double minPrice;
    private double maxPrice;
    private List<ProductCategory> categories;
}