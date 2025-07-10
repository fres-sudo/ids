package it.unicam.cs.ids.dtos.requests.company.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Rep
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EditCompanyRequest extends BaseCompanyRequest {
    /** New name of the company (if applicable) */
    private String name;
    /** New email to update to (if applicable) */
    private String email;
    /** New VAT number (if applicable) */
    private String vat;
    /** New password to update to (if applicable) */
    private String password;
    /** New description of the company */
    private String description;
    /** New phone number */
    private String phoneNumber;
    /** New address */
    private String address;
    /** New website */
    private String website;
    /** New billing information */
    private String billingInformation;
}
