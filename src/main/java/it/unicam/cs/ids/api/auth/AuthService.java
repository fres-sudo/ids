package it.unicam.cs.ids.api.auth;

import it.unicam.cs.ids.api.auth.dto.RegisterCompanyRequest;
import it.unicam.cs.ids.exceptions.auth.AuthenticationException;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.exceptions.auth.NotFound;
import it.unicam.cs.ids.exceptions.auth.NotUniqueEmail;
import it.unicam.cs.ids.mappers.CompanyMapper;
import it.unicam.cs.ids.mappers.UserMapper;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.services.EmailValidatorService;
import it.unicam.cs.ids.utils.InfrastructureTools;
import it.unicam.cs.ids.utils.Messages;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.unicam.cs.ids.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.unicam.cs.ids.api.auth.dto.AuthResponse;
import it.unicam.cs.ids.api.auth.dto.LoginRequest;
import it.unicam.cs.ids.api.auth.dto.RegisterUserRequest;
import it.unicam.cs.ids.api.auth.jwt.JwtTokenProvider;
import it.unicam.cs.ids.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


/**
 * Responsible for handling authentication and user registration logic.
 * <p>
 * This class delegates security operations to Spring Security components like
 * {@link AuthenticationManager}, {@link PasswordEncoder}, and the custom {@link JwtTokenProvider}.
 * </p>
 */
@Service
@AllArgsConstructor
public class AuthService implements AuthOperations {
    /** Service for validating email addresses */
    private final EmailValidatorService emailValidatorService;
    /** Authentication manager for processing authentication requests */
    private final AuthenticationManager authenticationManager;
    /** Repository for user data access */
    private final UserRepository userRepository;
    /** Repository for company data access */
    private final CompanyRepository companyRepository;
    /** Mapper for converting between DTOs and User entities */
    private final UserMapper userMapper;
    /** Mapper for converting between DTOs and Company entities */
    private final CompanyMapper companyMapper;
    /** Password encoder for hashing and verifying passwords */
    private final PasswordEncoder passwordEncoder;
    /** JWT token provider for generating and validating JWT tokens */
    private final JwtTokenProvider tokenProvider;

    public AuthResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Try User first
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            InfrastructureTools.validatePassword(passwordEncoder, password, user.getHashedPassword());
        } else {
            // Try Company next
            Company company = companyRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            InfrastructureTools.validatePassword(passwordEncoder, password, company.getHashedPassword());
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String token = tokenProvider.generateToken(authentication);
        return new AuthResponse(token);
    }

    @Override
    public void registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        emailValidatorService.validateEmailInUse(registerUserRequest.getEmail());
        User user = userMapper.fromRequest(registerUserRequest);
        userRepository.save(user);
    }

    @Override
    public void registerCompany(@RequestBody RegisterCompanyRequest registerCompanyRequest) {
        emailValidatorService.validateEmailInUse(registerCompanyRequest.getEmail());
        Company company = companyMapper.fromRequest(registerCompanyRequest);
        companyRepository.save(company);
    }

    @Override
    public Company getAuthenticatedCompany() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException(Messages.Auth.UNAUTHORIZED_ACCESS);
        }
        String email = authentication.getName();
        return companyRepository.findByEmail(email)
                .orElseThrow(() -> new NotFound(email));
    }


}
