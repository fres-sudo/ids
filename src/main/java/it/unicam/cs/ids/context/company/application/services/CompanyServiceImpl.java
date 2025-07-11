package it.unicam.cs.ids.context.company.application.services;

import it.unicam.cs.ids.context.identity.application.services.AuthService;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.requests.EditCompanyRequest;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CompanyServiceImpl implements CompanyService {

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
