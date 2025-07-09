package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.requests.CreateCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.mappers.CompanyMapper;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.utils.Messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public ApiResponse<Company> createCompany(CreateCompanyRequest request) {
       return null;
    }
}
