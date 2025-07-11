package it.unicam.cs.ids.context.catalog.infrastructure.web.dtos;

import it.unicam.cs.ids.context.catalog.domain.model.ProductCategory;
import it.unicam.cs.ids.shared.application.FilterParam;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class ProductFilter extends FilterParam {
    private List<ProductCategory> categories;
    private boolean available;
    private boolean availableForShipping;
    private long producerId;
    private double minPrice;
    private double maxPrice;
}