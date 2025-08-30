package it.unicam.cs.ids.context.identity.application.mappers;


import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.RegisterUserRequest;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.EditUserRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.shared.application.Validator;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {Validator.class}
)
@Component
public abstract class UserMapper {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    public abstract UserDTO toDto(User company);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "emailVerified", constant = "true")
    @Mapping(target = "verifiedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "role", expression = "java(it.unicam.cs.ids.context.identity.domain.model.PlatformRoles.BUYER)")
    public abstract User fromRequest(RegisterUserRequest request);

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