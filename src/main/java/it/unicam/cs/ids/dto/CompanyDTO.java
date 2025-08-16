package it.unicam.cs.ids.dto;

import it.unicam.cs.ids.shared.kernel.enums.CompanyRoles;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * CompanyDTO is used to represent a company in the system.
 * @see DTO
 */
@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CompanyDTO extends DTO {
    private Long id;
    private String name;
    private String website;
    private String email;
    private String description;
    private String address;
    private String phoneNumber;
    private Date createdAt;
    private CompanyRoles role;
}
