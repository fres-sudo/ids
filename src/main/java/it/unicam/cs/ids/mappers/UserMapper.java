package it.unicam.cs.ids.mappers;


import it.unicam.cs.ids.api.auth.dto.RegisterCompanyRequest;
import it.unicam.cs.ids.api.auth.dto.RegisterUserRequest;
import it.unicam.cs.ids.dtos.requests.user.config.EditUserRequest;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.entities.User;
import it.unicam.cs.ids.repositories.UserRepository;
import it.unicam.cs.ids.utils.InfrastructureTools;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {InfrastructureTools.class}
)
@Component
public abstract class UserMapper {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "emailVerified", constant = "true")
    @Mapping(target = "verifiedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "role", expression = "java(it.unicam.cs.ids.enums.PlatformRoles.BUYER)")
    public abstract User fromRequest(RegisterUserRequest request);

    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "name", source = "request.name", qualifiedByName = "validateString")
    @Mapping(target = "surname", source = "request.surname", qualifiedByName = "validateString")
    @Mapping(target = "email", source = "request.email", qualifiedByName = "validateEmail") // validate email
    @Mapping(target = "hashedPassword", source = "request.password", qualifiedByName = "encodePassword")
    @Mapping(target = "phoneNumber", source = "request.phoneNumber", qualifiedByName = "validatePhoneNumber")
    @Mapping(target = "address", source = "request.address", qualifiedByName = "validateString")
    public abstract User updateUserFromRequest(
            @MappingTarget User authenticatedUser,
            EditUserRequest request
    );


    @Named("mapUserById")
    public User fromId(Long id) {
        if (id == null) return null;
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
    }


    @Named("mapUserByIdMany")
    public List<User> fromIdMany(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return null;
        return ids.stream()
                .map(this::fromId)
                .toList();
    }

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