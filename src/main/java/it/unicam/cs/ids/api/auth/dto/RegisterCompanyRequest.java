package it.unicam.cs.ids.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Represents a request to register a new company.
 * This class contains the necessary information for creating a company account
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class RegisterCompanyRequest implements Serializable {
    /** The name of the company */
    private String name;
    /** The mail email address of the company */
    private String email;
    /** The password for the company account */
    private String password;
    /** The VAT number of the company */
    private String vat;
}