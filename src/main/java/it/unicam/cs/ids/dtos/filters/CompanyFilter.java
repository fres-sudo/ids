package it.unicam.cs.ids.dtos.filters;

import it.unicam.cs.ids.enums.ProductCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data @EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CompanyFilter extends FilterParam implements Serializable {
    private int minCompanySize;
    private int maxCompanySize;
}