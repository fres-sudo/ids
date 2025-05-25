package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.dtos.DTO;
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
    private long productId;
    private String name;
    private long issuerId;
    private String certificateUrl;
    private Date issueDate;
    private Date expirationDate;

}
