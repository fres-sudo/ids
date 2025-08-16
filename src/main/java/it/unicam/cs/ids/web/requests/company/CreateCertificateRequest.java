package it.unicam.cs.ids.web.requests.company;

import it.unicam.cs.ids.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * CreateCertificateRequest is used to create a new certificate for a product.
 */
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreateCertificateRequest extends DTO {
    private Long productId;
    private String name;
    private String description;
    private Long issuerId;
    private Date certificationDate;
    private Date issueDate;
    private Date expirationDate;
}
