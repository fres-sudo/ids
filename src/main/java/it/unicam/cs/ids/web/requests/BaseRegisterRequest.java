package it.unicam.cs.ids.web.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
    @Email
    private String email;
    @Min(value = 3, message = "Password must be at least 3 characters long")
    private String password;
}
