package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dto.CompanyDTO;
import it.unicam.cs.ids.web.requests.company.EditCompanyRequest;

/**
 * Service interface for managing  company-related operations.
 */
public interface CompanyService {
    /**
     * Modifies an existing company based on the provided requests.
     *
     * @param request the requests containing the company modification details
     * @return the (same) modified company
     */
    CompanyDTO editCompany(EditCompanyRequest request);

    /**
     * Deletes a company based on who made the requests.
     */
    void deleteCompany();

}
