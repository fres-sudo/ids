package it.unicam.cs.ids.shared.infrastructure.api.filters;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * BundleFilter is a filter class used to specify search criteria for bundles.
 * It extends FilterParam and includes a minimum discount percentage.
 */
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class BundleFilter extends FilterParam {
    private double minDiscountPercentage;
}