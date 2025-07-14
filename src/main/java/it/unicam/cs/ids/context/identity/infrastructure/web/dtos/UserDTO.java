package it.unicam.cs.ids.context.identity.infrastructure.web.dtos;

import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * UserDTO is used to transfer user data between different layers of the application.
 * @see DTO
 */
@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDTO extends DTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private PlatformRoles role;
}
