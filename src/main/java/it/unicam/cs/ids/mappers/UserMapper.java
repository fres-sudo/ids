package it.unicam.cs.ids.mappers;


import it.unicam.cs.ids.web.requests.user.RegisterUserRequest;
import it.unicam.cs.ids.dto.UserDTO;
import it.unicam.cs.ids.web.requests.user.EditUserRequest;
import it.unicam.cs.ids.models.User;
import it.unicam.cs.ids.repositories.UserRepository;
import it.unicam.cs.ids.shared.application.Validator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting {@link RegisterUserRequest} and {@link EditUserRequest} to {@link User} entity.
 * This class provides methods to map user-related requests to the User entity and vice versa.
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {Validator.class}
)
@Component
public abstract class UserMapper {
    /** Repository for accessing User entities */
    @Autowired
    protected UserRepository userRepository;
    /** Password encoder for hashing passwords */
    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * Converts a {@link User} entity to a {@link UserDTO}.
     * This method maps the fields of the User entity to the corresponding fields in the DTO.
     *
     * @param company the User entity to be converted
     * @return a new UserDTO instance with the details from the User entity
     */
    public abstract UserDTO toDto(User company);

    /**
     * Converts a {@link RegisterUserRequest} to a {@link User} entity.
     * This method maps all the request fields to the User entity, ignoring certain fields that are not applicable during the creation phase,
     * such as ID, createdAt, updatedAt, and deletedAt.
     *
     * @param request the RegisterUserRequest containing user details
     * @return a new User instance populated with values from the request
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "emailVerified", constant = "true")
    @Mapping(target = "verifiedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "role", expression = "java(it.unicam.cs.ids.shared.kernel.enums.PlatformRoles.BUYER)")
    public abstract User fromRequest(RegisterUserRequest request);

    /**
     * Updates an existing {@link User} entity with the data from an {@link EditUserRequest}.
     * The method maps the request fields to the corresponding User fields, updating the existing entity in place.
     *
     * @param authenticatedUser the existing User entity to be updated
     * @param request the EditUserRequest containing updated user details
     * @return the updated User entity
     */
    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "name", source = "name", qualifiedByName = "validateString")
    @Mapping(target = "surname", source = "surname", qualifiedByName = "validateString")
    @Mapping(target = "email", source = "email", qualifiedByName = "validateEmail") // validate email
    @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "validatePhoneNumber")
    @Mapping(target = "address", source = "address", qualifiedByName = "validateString")
    public abstract User updateUserFromRequest(
            @MappingTarget User authenticatedUser,
            EditUserRequest request
    );

    /**
     * Maps a raw password to an encoded password using the configured PasswordEncoder.
     *
     * @param rawPassword the raw password to encode
     * @return the encoded password
     */
    @Named("encodePassword")
    protected String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}