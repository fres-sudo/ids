package it.unicam.cs.ids.context.identity.application.strategies.impl;

import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.identity.application.strategies.RegistrationStrategy;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterCompanyRequest;
import it.unicam.cs.ids.shared.application.EmailValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Registration strategy for companies.
 * Handles the registration logic specific to Company entities.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyRegistrationStrategy implements RegistrationStrategy<Company, RegisterCompanyRequest> {
    
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final EmailValidatorService emailValidatorService;
    
    @Override
    public Company register(RegisterCompanyRequest request) {
        validateRequest(request);
        
        Company company = companyMapper.fromRequest(request);
        
        // Set default role if not specified
        if (company.getRole() == null) {
            company.setRole(CompanyRoles.PRODUCER);
        }
        
        return companyRepository.save(company);
    }
    
    @Override
    public boolean canHandle(Class<?> requestType) {
        return RegisterCompanyRequest.class.equals(requestType);
    }
    
    @Override
    public void validateRequest(RegisterCompanyRequest request) {
        // Basic null check - other validations handled by @Valid annotations
        if (request == null) {
            throw new IllegalArgumentException("Registration request cannot be null");
        }
        
        // Business validations - check uniqueness constraints
        emailValidatorService.validateEmailInUse(request.getEmail());
        
        if (companyRepository.existsByVat(request.getVat())) {
            throw new IllegalArgumentException("VAT number is already in use");
        }
    }
    
    @Override
    public Class<Company> getEntityType() {
        return Company.class;
    }
    
    @Override
    public Class<RegisterCompanyRequest> getRequestType() {
        return RegisterCompanyRequest.class;
    }
}