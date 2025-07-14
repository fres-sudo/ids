package it.unicam.cs.ids.context.company.infrastructure.web.dtos;

import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import it.unicam.cs.ids.shared.application.FilterParam;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFilter implements Serializable {
    private CompanyRoles companyRole;
    private String searchBy;
}