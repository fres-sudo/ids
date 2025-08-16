package it.unicam.cs.ids.shared.infrastructure.api.filters;

import it.unicam.cs.ids.shared.kernel.enums.ProductCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

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