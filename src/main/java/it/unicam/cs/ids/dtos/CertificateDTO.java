package it.unicam.cs.ids.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * CertificateDTO is used to represent a certificate issued for a product.
 * @see DTO
 */
@Data @EqualsAndHashCode(callSuper = false)
public class CertificateDTO extends DTO {
    private long id;
    private ProductDTO product;
    private CompanyDTO issuer;
    private String name;
    private Date issueDate;
    private Date expirationDate;
    private String certificateUrl;
    private Date createdAt;
}