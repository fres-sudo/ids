package it.unicam.cs.ids.dtos.filters;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data @EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFilter extends FilterParam {
    private String companyType;
}