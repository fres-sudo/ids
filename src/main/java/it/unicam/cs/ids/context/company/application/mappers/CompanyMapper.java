package it.unicam.cs.ids.context.company.application.mappers;


import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterCompanyRequest;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.requests.EditCompanyRequest;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.shared.application.Validator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {Validator.class}
)
public abstract class CompanyMapper {

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public abstract CompanyDTO toDto(Company company);


    /**
     * Converts a {@link Company} entity to a {@link CompanyDTO}.
     * @param request the RegisterCompanyRequest containing company details
     * @return a new Company entity populated with values from the requests
     * This method maps ALL the requests fields to the Company entity,
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true) // ignore audit fields
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true) // ignore deletedAt for new entities
    @Mapping(target = "email", source = "email", qualifiedByName = "validateEmail") // validate email
    @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "encodePassword") // encode password
    @Mapping(target = "vat", source = "vat", qualifiedByName = "validateVat") // validate VAT
    @Mapping(target = "emailVerified", constant = "true") // default to false, or true for testing
    @Mapping(target = "verifiedAt", ignore = true) // ignore date, set later if needed
    @Mapping(target = "description", ignore = true) // map only fields from requests, ignore others
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "website", ignore = true)
    @Mapping(target = "billingInformation", ignore = true)
    @Mapping(target = "role", expression = "java(request.getRole())")
    public abstract Company fromRequest(RegisterCompanyRequest request);

    /**
     * Updates an existing Company entity with values from the {@link EditCompanyRequest}.
     * @param existing the existing Company entity to update
     * @param request the EditCompanyRequest containing new values
     * @return the updated Company entity
     * Ignores fields that are not present in the requests, such as id, createdAt, and updatedAt.
     */
    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "name", source = "name", qualifiedByName = "validateString")
    @Mapping(target = "email", source = "email", qualifiedByName = "validateEmail") // validate email
    @Mapping(target = "vat", source = "vat", qualifiedByName = "validateVat") // validate VAT
    @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "description", source = "description", qualifiedByName = "validateString")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "validatePhoneNumber")
    @Mapping(target = "address", source = "address", qualifiedByName = "validateString")
    @Mapping(target = "website", source = "website", qualifiedByName = "validateString")
    @Mapping(target = "billingInformation", source = "billingInformation")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "verifiedAt", ignore = true)
    public abstract Company updateCompanyFromRequest(
            @MappingTarget Company existing,
            EditCompanyRequest request
    );

    /**
     * Maps an individual company ID to its corresponding Company entity.
     * @param id the ID of the company to map
     * @return the Company entity corresponding to the provided ID
     */
    @Named("mapCompanyById")
    public Company fromId(Long id) {
        if (id == null) return null;
        return companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company with ID " + id + " not found"));
    }

    /**
     * Maps a list of company IDs to their corresponding Company entities.
     * @param ids the list of company IDs to map
     * @return a list of Company entities corresponding to the provided IDs
     * Returns null if the input list is null or empty.
     */
    @Named("mapCompanyByIdMany")
    public List<Company> fromIdMany(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return null;
        return ids.stream()
                .map(this::fromId)
                .toList();
    }

    /**
     * Encodes a raw password using the configured PasswordEncoder.
     * @param rawPassword the plain text password to encode
     * @return the encoded password
     * @throws IllegalArgumentException if the raw password is null or empty
     */
    @Named("encodePassword")
    public String encodePassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return passwordEncoder.encode(rawPassword);
    }
}
