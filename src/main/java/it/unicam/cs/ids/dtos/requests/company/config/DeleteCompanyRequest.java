package it.unicam.cs.ids.dtos.requests.company.config;


import it.unicam.cs.ids.dtos.requests.company.BaseCompanyRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a request to delete a company.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeleteCompanyRequest extends BaseCompanyRequest {
    /** The email of the company to be deleted. */
    private String email;
    /** The password of the company to be deleted. */
    private String password;
    /** The VAT number of the company to be deleted. */
    private String vat;
}
