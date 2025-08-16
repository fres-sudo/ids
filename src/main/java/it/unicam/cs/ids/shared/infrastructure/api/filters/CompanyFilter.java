package it.unicam.cs.ids.shared.infrastructure.api.filters;

import it.unicam.cs.ids.shared.kernel.enums.CompanyRoles;
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