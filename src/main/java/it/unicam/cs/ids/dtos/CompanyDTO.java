package it.unicam.cs.ids.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private String socialReason;
    private long vat;
    private String address;
    private String phoneNumber;
    private String billingInformation;
    private List<UserDTO> employees;
    private Date createdAt;
}
