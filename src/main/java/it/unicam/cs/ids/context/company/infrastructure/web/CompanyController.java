package it.unicam.cs.ids.context.company.infrastructure.web;


import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.requests.EditCompanyRequest;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.application.services.CompanyService;
import it.unicam.cs.ids.shared.application.Messages;
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
     * @param request the requests containing the details to edit the company
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
