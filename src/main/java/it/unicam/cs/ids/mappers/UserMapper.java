package it.unicam.cs.ids.mappers;


import it.unicam.cs.ids.api.auth.dto.RegisterCompanyRequest;
import it.unicam.cs.ids.api.auth.dto.RegisterUserRequest;
import it.unicam.cs.ids.entities.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "hashedPassword", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "emailVerified", constant = "true")
    @Mapping(target = "verifiedAt", expression = "java(new java.util.Date())")
    @Mapping(target = "company", ignore = true)
    public abstract User fromRequest(RegisterUserRequest request);

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