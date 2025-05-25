package it.unicam.cs.ids.dtos.filters;

import it.unicam.cs.ids.enums.ProductCategory;
import lombok.*;

import java.util.List;

@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductFilter extends FilterParam {
    private List<ProductCategory> categories;
    private boolean available;
    private boolean availableForShipping;
    private long producerId;
}