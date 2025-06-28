package it.unicam.cs.ids.dtos.filters;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data @EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CompanyFilter extends FilterParam {
    private int minCompanySize;
    private int maxCompanySize;
}