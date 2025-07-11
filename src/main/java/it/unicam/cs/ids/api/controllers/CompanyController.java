package it.unicam.cs.ids.api.controllers;


import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.CompanyDTO;
import it.unicam.cs.ids.dtos.requests.company.config.EditCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.services.CompanyService;
import it.unicam.cs.ids.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing company-related operations.
 *
 * @see CompanyService
 * @see CompanyController
 * @see Company
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("companies")
public class CompanyController {
    /**
     * Service for handling company-related operations
     */
    private final CompanyService companyService;
    /**
     * Factory for creating API responses
     */
    private final ApiResponseFactory responseFactory;

    /**
     * Edits an existing company.
     *
     * @param request the request containing the details to edit the company
     * @return a response entity containing the updated company
     */
    @PatchMapping()
    ResponseEntity<ApiResponse<CompanyDTO>> editCompany(@RequestBody EditCompanyRequest request) {
        CompanyDTO dto = companyService.editCompany(request);
        ApiResponse<CompanyDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.COMPANY_UPDATED, dto
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a company.
     *
     * @return a response entity indicating success or failure
     */
    @DeleteMapping()
    ResponseEntity<ApiResponse<Void>> deleteCompany() {
        companyService.deleteCompany();
        ApiResponse<Void> response = responseFactory.createSuccessResponse(
                Messages.Success.COMPANY_DELETED, null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
