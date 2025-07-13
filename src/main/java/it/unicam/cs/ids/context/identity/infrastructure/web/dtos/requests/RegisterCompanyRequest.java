package it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests;

import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Represents a requests to register a new company.
 * This class contains the necessary information for creating a company account
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterCompanyRequest extends BaseRegisterRequest {
    /** The name of the company */
    private String name;
    /** The VAT number of the company */
    private String vat;
    /** The role of the company */
    private CompanyRoles role = CompanyRoles.PRODUCER; // Default role for the company
}