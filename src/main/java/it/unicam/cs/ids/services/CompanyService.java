package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.CompanyDTO;
import it.unicam.cs.ids.dtos.requests.company.config.DeleteCompanyRequest;
import it.unicam.cs.ids.dtos.requests.company.config.EditCompanyRequest;
import it.unicam.cs.ids.entities.Company;

/**
 * Service interface for managing  company-related operations.
 */
public interface CompanyService {
    /**
     * Modifies an existing company based on the provided request.
     *
     * @param request the request containing the company modification details
     * @return the (same) modified company
     */
    CompanyDTO editCompany(EditCompanyRequest request);

    /**
     * Deletes a company based on who made the request.
     */
    void deleteCompany();

}
