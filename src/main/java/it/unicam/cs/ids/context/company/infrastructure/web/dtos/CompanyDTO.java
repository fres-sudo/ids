package it.unicam.cs.ids.context.company.infrastructure.web.dtos;

import it.unicam.cs.ids.shared.application.DTO;
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
    private long id;
    private String name;
    private String website;
    private String email;
    private String description;
    private String address;
    private String phoneNumber;
    private Date createdAt;
}
