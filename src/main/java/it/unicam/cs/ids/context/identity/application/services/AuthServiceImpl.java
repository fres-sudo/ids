package it.unicam.cs.ids.context.identity.application.services;

import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterAdminRequest;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterCompanyRequest;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CertifierRequest;
import it.unicam.cs.ids.shared.application.Validator;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.AuthenticationException;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.NotFound;
import it.unicam.cs.ids.context.certification.application.mappers.CertifierMapper;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.certification.domain.repositories.CertifierRequestRepository;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.shared.application.EmailValidatorService;
import it.unicam.cs.ids.shared.application.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.responses.AuthResponse;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.LoginRequest;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterUserRequest;
import it.unicam.cs.ids.context.identity.infrastructure.security.jwt.JwtTokenProvider;
import it.unicam.cs.ids.context.identity.domain.model.User;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {
    /** Service for validating email addresses */
    private final EmailValidatorService emailValidatorService;
    /** Authentication manager for processing authentication requests */
    private final AuthenticationManager authenticationManager;
    /** Repository for user data access */
    private final UserRepository userRepository;
    /** Repository for company data access */
    private final CompanyRepository companyRepository;
    /** Repository for certifier requests data access */
    private final CertifierRequestRepository certifierRequestRepository;
    /** Mapper for converting between DTOs and User entities */
    private final UserMapper userMapper;
    /** Mapper for converting between DTOs and Company entities */
    private final CompanyMapper companyMapper;
    /** Mapper for converting between DTOs and Certifier entities */
    private final CertifierMapper certifierMapper;
    /** Password encoder for hashing and verifying passwords */
    @Getter
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
            Validator.validatePassword(passwordEncoder, password, user.getHashedPassword());
        } else {
            // Try Company next
            Company company = companyRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            Validator.validatePassword(passwordEncoder, password, company.getHashedPassword());
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
    public void registerCertifier(@RequestBody RegisterUserRequest registerUserRequest) {
        emailValidatorService.validateEmailInUse(registerUserRequest.getEmail());
        User certifier = userMapper.fromRequest(registerUserRequest);
        userRepository.save(certifier); //NOTE: First save the user to get the ID

        CertifierRequest certifierRequest = certifierMapper.fromUser(certifier);
        certifierRequestRepository.save(certifierRequest);
    }
    
    @Override
    public void registerAdmin(@RequestBody RegisterAdminRequest registerAdminRequest) {
        emailValidatorService.validateEmailInUse(registerAdminRequest.getEmail());
        // FIXME: -- TESTING PURPOSES ONLY --
        final String ADMIN_BY_PASS_PASSWORD =  "admin";
        if (!registerAdminRequest.getAdminByPassPasswordField().equals(
                ADMIN_BY_PASS_PASSWORD)
        ) {
            throw new AuthenticationException("ADMIN BY-PASS PASSWORD MISMATCH");
        }
        User admin = userMapper.fromRequest(registerAdminRequest);
        admin.setRole(PlatformRoles.ADMIN);
        userRepository.save(admin);
    }

    @Override
    public void registerCompany(@RequestBody RegisterCompanyRequest registerCompanyRequest) {
        emailValidatorService.validateEmailInUse(registerCompanyRequest.getEmail());

        Company company = companyMapper.fromRequest(registerCompanyRequest);
        companyRepository.save(company);
    }

    @Override
    public Company getAuthenticatedCompany() {
        String email = getAuthenticatedEmail();
        return companyRepository.findByEmail(email)
                .orElseThrow(() -> new NotFound(email));
    }

    @Override
    public User getAuthenticatedUser() {
        String email = getAuthenticatedEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFound(email));
    }

    private String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException(Messages.Auth.UNAUTHORIZED_ACCESS);
        }
        return authentication.getName();
    }
}
