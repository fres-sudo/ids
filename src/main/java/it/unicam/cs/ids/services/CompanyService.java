package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.requests.CreateCompanyRequest;
import it.unicam.cs.ids.entities.Company;

public interface CompanyService {
    ApiResponse<Company> createCompany(CreateCompanyRequest request);
}
