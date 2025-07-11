package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.auth.services.AuthService;
import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.factories.DefaultApiResponseFactory;
import it.unicam.cs.ids.dtos.CompanyDTO;
import it.unicam.cs.ids.dtos.requests.company.config.EditCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.mappers.CompanyMapper;
import it.unicam.cs.ids.repositories.CompanyRepository;

import it.unicam.cs.ids.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CompanyServiceImpl implements CompanyService {

    private final ApiResponseFactory apiResponseFactory = new DefaultApiResponseFactory();
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;
    private final AuthService authService;


    @Override
    public CompanyDTO editCompany(EditCompanyRequest request) {
        Company authenticatedCompany = authService.getAuthenticatedCompany();
        Company updatedCompany = companyMapper.updateCompanyFromRequest(authenticatedCompany, request);
        return companyMapper.toDto(companyRepository.save(updatedCompany));
    }

    @Override
    public void deleteCompany() {
        Company authenticatedCompany = authService.getAuthenticatedCompany();
        companyRepository.deleteById(authenticatedCompany.getId());
    }

}
