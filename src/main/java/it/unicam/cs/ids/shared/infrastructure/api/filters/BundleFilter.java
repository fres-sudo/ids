package it.unicam.cs.ids.shared.infrastructure.api.filters;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class BundleFilter extends FilterParam {
    private double minDiscountPercentage;
}