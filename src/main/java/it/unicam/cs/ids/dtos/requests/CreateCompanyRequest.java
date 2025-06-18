package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.dtos.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CreateCompanyRequest extends DTO {
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
    private String vat;
    private String billingInformation;
}
