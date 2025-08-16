package it.unicam.cs.ids.shared.infrastructure.api.filters;

import it.unicam.cs.ids.shared.kernel.enums.ProductCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * ProductFilter is a filter class used to specify search criteria for products.
 * It includes fields for product categories and producer ID.
 */
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class ProductFilter extends FilterParam {
    private List<ProductCategory> categories;
    private Long producerId;
}