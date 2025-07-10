package it.unicam.cs.ids.api.controllers;


import lombok.RequiredArgsConstructor;

import it.unicam.cs.ids.services.CompanyService;
import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.requests.company.config.DeleteCompanyRequest;
import it.unicam.cs.ids.dtos.requests.company.config.EditCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.utils.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing company-related operations.
 * @see CompanyService
 * @see CompanyController
 * @see Company
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("companies")
public class CompanyController {
    /** Service for handling company-related operations */
    private final CompanyService companyService;
    /** * Factory for creating API responses */
    private final ApiResponseFactory responseFactory;

    @PutMapping("edit")
    ResponseEntity<ApiResponse<Company>> EditCompany(@RequestBody EditCompanyRequest request) {
        Company updated               = companyService.editCompany(request);
        ApiResponse<Company> response = responseFactory.createSuccessResponse(
                Messages.Success.COMPANY_UPDATED, updated
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    ResponseEntity<ApiResponse<Void>> deleteCompany(@RequestBody DeleteCompanyRequest request) {
        companyService.deleteCompany(request);
        ApiResponse<Void> response = responseFactory.createSuccessResponse(
                Messages.Success.COMPANY_DELETED, null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
