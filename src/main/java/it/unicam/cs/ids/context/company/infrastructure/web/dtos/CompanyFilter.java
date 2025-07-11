package it.unicam.cs.ids.context.company.infrastructure.web.dtos;

import it.unicam.cs.ids.shared.application.FilterParam;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data @EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFilter extends FilterParam {
    private String companyType;
}