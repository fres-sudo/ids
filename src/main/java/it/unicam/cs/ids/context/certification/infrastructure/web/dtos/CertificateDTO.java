package it.unicam.cs.ids.context.certification.infrastructure.web.dtos;

import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * CertificateDTO is used to represent a certificate issued for a product.
 * @see DTO
 */
@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CertificateDTO extends DTO {
    private long id;
    private CompanyDTO issuer;
    private String name;
    private Date issueDate;
    private Date expirationDate;
    private String certificateUrl;
    private Date createdAt;
}