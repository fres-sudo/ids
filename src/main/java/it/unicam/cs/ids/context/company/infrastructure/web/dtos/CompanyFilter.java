package it.unicam.cs.ids.context.company.infrastructure.web.dtos;

import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFilter implements Serializable {
    private CompanyRoles companyRole;
    private String searchBy;
}