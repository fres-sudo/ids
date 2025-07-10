package it.unicam.cs.ids.dtos.requests.company.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeleteCompanyRequest extends BaseCompanyRequest {
    private String email;
    private String password;
    private String vat;
}
