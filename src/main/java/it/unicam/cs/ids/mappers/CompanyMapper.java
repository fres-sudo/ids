package it.unicam.cs.ids.mappers;


import it.unicam.cs.ids.api.auth.dto.RegisterCompanyRequest;
import it.unicam.cs.ids.dtos.CompanyDTO;
import it.unicam.cs.ids.dtos.requests.company.config.EditCompanyRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.repositories.CompanyRepository;
import it.unicam.cs.ids.utils.Validator;
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
@Component
public abstract class CompanyMapper {
    @Autowired  //todo: remove this and use constructor injection
    protected CompanyRepository companyRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    public abstract CompanyDTO toDto(Company company);


    /**
     * Converts a {@link Company} entity to a {@link CompanyDTO}.
     * @param request the RegisterCompanyRequest containing company details
     * @return a new Company entity populated with values from the request
     * This method maps ALL the request fields to the Company entity,
     */
    @Mapping(target = "id", ignore = true) // assuming id is in BaseEntity, ignore it
    @Mapping(target = "createdAt", ignore = true) // ignore audit fields
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true) // ignore deletedAt for new entities
    @Mapping(target = "email", source = "request.email", qualifiedByName = "validateEmail") // validate email
    @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "encodePassword") // encode password
    @Mapping(target = "vat", source = "request.vat", qualifiedByName = "validateVat") // validate VAT
    @Mapping(target = "emailVerified", constant = "true") // default to false, or true for testing
    @Mapping(target = "verifiedAt", ignore = true) // ignore date, set later if needed
    @Mapping(target = "description", ignore = true) // map only fields from request, ignore others
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "website", ignore = true)
    @Mapping(target = "billingInformation", ignore = true)
    public abstract Company fromRequest(RegisterCompanyRequest request);

    /**
     * Updates an existing Company entity with values from the {@link EditCompanyRequest}.
     * @param existing the existing Company entity to update
     * @param request the EditCompanyRequest containing new values
     * @return the updated Company entity
     * Ignores fields that are not present in the request, such as id, createdAt, and updatedAt.
     */
    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "name", source = "request.name", qualifiedByName = "validateString")
    @Mapping(target = "email", source = "request.email", qualifiedByName = "validateEmail") // validate email
    @Mapping(target = "vat", source = "request.vat", qualifiedByName = "validateVat") // validate VAT
    @Mapping(target = "hashedPassword", source = "request.password", qualifiedByName = "encodePassword")
    @Mapping(target = "description", source = "request.description", qualifiedByName = "validateString")
    @Mapping(target = "phoneNumber", source = "request.phoneNumber", qualifiedByName = "validatePhoneNumber")
    @Mapping(target = "address", source = "request.address", qualifiedByName = "validateString")
    @Mapping(target = "website", source = "request.website", qualifiedByName = "validateString")
    @Mapping(target = "billingInformation", source = "request.billingInformation")
    public abstract Company updateCompanyFromRequest(
            @MappingTarget Company existing,
            EditCompanyRequest request
    );

    /**
     * Maps a individual company ID to its corresponding Company entity.
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
