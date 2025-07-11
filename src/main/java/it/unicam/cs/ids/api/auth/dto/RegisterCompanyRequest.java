package it.unicam.cs.ids.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Represents a request to register a new company.
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
}