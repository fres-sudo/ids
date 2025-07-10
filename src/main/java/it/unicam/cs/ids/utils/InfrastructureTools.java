package it.unicam.cs.ids.utils;

import it.unicam.cs.ids.dtos.requests.company.config.DeleteCompanyRequest;
import it.unicam.cs.ids.dtos.requests.user.config.DeleteUserRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.entities.User;
import it.unicam.cs.ids.exceptions.auth.AuthenticationException;
import lombok.NonNull;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
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
     * @param encoder the PasswordEncoder to use for password validation
     * @param company the company to validate against
     * @param request the deletion request containing email, VAT, and hashed password
     * @throws AuthenticationException if the validation fails
     */
    public static void validateCompanyDelete(PasswordEncoder encoder, Company company, DeleteCompanyRequest request) {
        if (company.getEmail().equals(request.getEmail()) &&
            company.getVat().equals(request.getVat())) {
            validatePassword(encoder, request.getPassword(), company.getHashedPassword());
        }

    }

    /**
     * Validates the user deletion request by checking if the provided email and hashed password match
     * the user's details.
     *
     * @param passwordEncoder the PasswordEncoder to use for password validation
     * @param user the user to validate against
     * @param request the deletion request containing email and password
     * @throws AuthenticationException if the validation fails
     */
    public static void validateUserDelete(PasswordEncoder passwordEncoder, User user, DeleteUserRequest request) {
        if (user.getEmail().equals(request.getEmail())){
            validatePassword(passwordEncoder, request.getPassword(), user.getHashedPassword());
        }
    }

    /**
     * Validates the provided email address against a standard email format.
     * @param email the email address to validate
     * @return the email if it is valid
     * @throws IllegalArgumentException if the email is null, empty
     * @throws java.util.regex.PatternSyntaxException if the regex pattern is invalid
     */
    @Named("validateEmail")
    public String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return email;
    }

    /**
     * Validates the provided VAT number against a standard VAT format.
     * @param vat the VAT number to validate
     * @return true if the VAT is valid, false otherwise
     * @throws IllegalArgumentException if the VAT is null, empty, or does not match the expected format
     * @throws java.util.regex.PatternSyntaxException if the regex pattern is invalid
     */
    @Named("validateVat")
    public String validateVat(String vat) {
        if (vat == null || vat.isBlank()) {
            throw new IllegalArgumentException("VAT cannot be empty");
        }
        String vatRegex = "^[A-Z]{2}[0-9A-Z]{8,12}$";
        Pattern pattern = Pattern.compile(vatRegex);

        if (!pattern.matcher(vat).matches()) {
            throw new IllegalArgumentException("Invalid VAT format");
        }

        return vat;
    }

    /**
     * Validates the provided phone number against a standard phone number format.
     * example formats include:
     * - "+1234567890"
     * - "123-456-7890"
     * @param phoneNumber the phone number to validate
     * @return the phone number if it is valid
     * @throws IllegalArgumentException if the phone number is null, empty, or does not match the expected format
     * @throws java.util.regex.PatternSyntaxException if the regex pattern is invalid
     */
    @Named("validatePhoneNumber")
    public String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        String phoneRegex = "^\\+?[0-9]{1,3}?[-.\\s]?\\(?[0-9]{1,4}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$";
        Pattern pattern = Pattern.compile(phoneRegex);

        if (!pattern.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        return phoneNumber;
    }

    /**
     * Validates a string value to ensure it is not null or empty.
     * @param value the string value to validate
     * @return the validated string
     * @throws IllegalArgumentException if the value is null or empty
     */
    @Named("validateString")
    public String validateString(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Critical Field cannot be empty");
        }
        return value;
    }
}
