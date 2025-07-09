package it.unicam.cs.ids.dtos;

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