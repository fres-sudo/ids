package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.shared.kernel.exceptions.auth.NotUniqueEmail;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*+
  * Service for validating email addresses to ensure they are not already in use.
 */
@Service
@RequiredArgsConstructor
public class EmailValidatorService {
    /** Repository for user data access */
    private final UserRepository userRepository;
    /** Repository for company data access */
    private final CompanyRepository companyRepository;

    /**
     * Validates that the provided email is not already in use by either a user or a company.
     *
     * @param email the email address to validate
     * @throws NotUniqueEmail if the email is already in use
     */
    public void validateEmailInUse(String email) {
        if (userRepository.existsByEmail(email) || companyRepository.existsByEmail(email)) {
            throw new NotUniqueEmail();
        }
    }
}
