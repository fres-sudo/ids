package it.unicam.cs.ids.context.identity.application.providers.impl;

import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.identity.application.providers.AuthenticationProvider;
import it.unicam.cs.ids.shared.application.Validator;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Authentication provider for Company entities.
 * Handles authentication logic specific to companies.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyAuthenticationProvider implements AuthenticationProvider<Company> {
    
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public boolean supports(String email) {
        return companyRepository.existsByEmail(email);
    }
    
    @Override
    public Company authenticate(String email, String password) {
        Optional<Company> companyOpt = companyRepository.findByEmail(email);
        
        if (companyOpt.isEmpty()) {
            throw new AuthenticationException("Invalid credentials");
        }
        
        Company company = companyOpt.get();
        
        // If password is provided, validate it. If null, assume already authenticated
        if (password != null) {
            Validator.validatePassword(passwordEncoder, password, company.getHashedPassword());
        }
        
        return company;
    }
    
    @Override
    public String getEntityType() {
        return "COMPANY";
    }
    
    @Override
    public Class<Company> getEntityClass() {
        return Company.class;
    }
}