package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.auth.services.AuthService;
import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.dtos.requests.company.config.DeleteCompanyRequest;
import it.unicam.cs.ids.dtos.requests.company.config.EditCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.mappers.CompanyMapper;
import it.unicam.cs.ids.repositories.CompanyRepository;

import it.unicam.cs.ids.utils.InfrastructureTools;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;
    private final AuthService authService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper, AuthService authService) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.authService = authService;
    }

    @Override
    public Company editCompany(EditCompanyRequest request) {
        Company authenticatedCompany = authService.getAuthenticatedCompany();
        Company updatedCompany = companyMapper.updateCompanyFromRequest(authenticatedCompany, request);

        return companyRepository.save(updatedCompany);
    }

    @Override
    public void deleteCompany(DeleteCompanyRequest request) {
        Company authenticatedCompany = authService.getAuthenticatedCompany();
        PasswordEncoder passwordEncoder = authService.getPasswordEncoder();
        InfrastructureTools.validateCompanyDelete(passwordEncoder, authenticatedCompany, request);
        companyRepository.delete(authenticatedCompany);
    }

}
