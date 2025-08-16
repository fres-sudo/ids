package it.unicam.cs.ids.shared.infrastructure.api.filters;

import it.unicam.cs.ids.shared.kernel.enums.CompanyRoles;
import lombok.*;

import java.io.Serializable;

/**
 * CompanyFilter is a filter class used to specify search criteria for companies.
 * It includes the company role and a search term.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFilter implements Serializable {
    private CompanyRoles companyRole;
    private String searchBy;
}