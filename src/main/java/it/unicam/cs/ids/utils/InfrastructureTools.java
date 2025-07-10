package it.unicam.cs.ids.utils;

import it.unicam.cs.ids.dtos.requests.company.config.DeleteCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.exceptions.auth.AuthenticationException;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class InfrastructureTools {
    private InfrastructureTools() {}

    /**
     * Validates the provided password against the hashed password using the specified PasswordEncoder.
     *
     * @param passwordEncoder the PasswordEncoder to use for validation
     * @param password the plain text password to validate
     * @param hashedPassword the hashed password to compare against
     * @throws AuthenticationException if the password does not match the hashed password
     */
    public static void validatePassword(
            @NonNull PasswordEncoder passwordEncoder,
            @NonNull String password,
            @NonNull String hashedPassword
    ) {
        if (!passwordEncoder.matches(password, hashedPassword)) {
            throw new AuthenticationException(Messages.Auth.INVALID_PASSWORD);
        }
    }


    /**
     * Validates the company deletion request by checking if the provided email, VAT, and hashed password match
     * the company's details.
     *
     * @param company the company to validate against
     * @param request the deletion request containing email, VAT, and hashed password
     * @param requestHashedPassword the hashed password from the request
     * @throws AuthenticationException if the validation fails
     */
    public static void validateCompanyDelete(Company company, DeleteCompanyRequest request, String requestHashedPassword) {
        if (company.getEmail().equals(request.getEmail()) &&
            company.getVat().equals(request.getVat()) &&
            company.getHashedPassword().equals(requestHashedPassword)) return;
        else {
            throw new AuthenticationException(Messages.Auth.INVALID_COMPANY_DELETE_REQUEST);
        }
    }
}
