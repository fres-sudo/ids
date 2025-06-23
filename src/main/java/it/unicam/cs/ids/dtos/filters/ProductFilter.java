package it.unicam.cs.ids.dtos.filters;

import it.unicam.cs.ids.enums.ProductCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class ProductFilter extends FilterParam implements Serializable {
    private List<ProductCategory> categories;
    private boolean available;
    private boolean availableForShipping;
    private long producerId;
    private double minPrice;
    private double maxPrice;
}