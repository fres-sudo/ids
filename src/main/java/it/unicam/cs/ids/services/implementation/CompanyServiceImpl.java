package it.unicam.cs.ids.services.implementation;

import it.unicam.cs.ids.dto.CompanyDTO;
import it.unicam.cs.ids.web.requests.company.EditCompanyRequest;
import it.unicam.cs.ids.model.Company;
import it.unicam.cs.ids.mappers.CompanyMapper;
import it.unicam.cs.ids.repositories.CompanyRepository;

import it.unicam.cs.ids.services.AuthService;
import it.unicam.cs.ids.services.CompanyService;
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
