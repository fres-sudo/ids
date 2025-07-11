package it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Represents a base request for user registration.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public abstract class BaseRegisterRequest implements Serializable {
    /** The name of the issuer */
    private String email;
    /** The password for the issuer account */
    private String password;
}
