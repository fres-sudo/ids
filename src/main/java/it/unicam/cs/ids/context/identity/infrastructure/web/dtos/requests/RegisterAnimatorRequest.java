package it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests;

import it.unicam.cs.ids.context.company.domain.models.CompanyRoles;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Represents a requests to register a new animator to the platform.
 * This class contains the necessary information for creating an animator account
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterAnimatorRequest extends RegisterUserRequest { }